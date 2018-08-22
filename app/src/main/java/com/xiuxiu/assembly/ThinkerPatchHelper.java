package com.xiuxiu.assembly;

import android.util.Log;

import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;
import com.tinkerpatch.sdk.server.callback.ConfigRequestCallback;
import com.tinkerpatch.sdk.server.callback.RollbackCallBack;
import com.tinkerpatch.sdk.tinker.callback.ResultCallBack;
import com.xiuxiu.BuildConfig;

import java.util.HashMap;

/**
 * Created by ${weicheng} on 2018/8/22.
 */

public class ThinkerPatchHelper {

    private ApplicationLike tinkerApplicationLike;
    private final String TAG = this.getClass().getName();
    private static ThinkerPatchHelper thinkerPatchHelper;
    private ThinkerPatchHelper() {

    }

    public static void install() {
        if(thinkerPatchHelper == null){
            thinkerPatchHelper = new ThinkerPatchHelper();
        }
        thinkerPatchHelper.initTinkerPatch();
    }

    private void initTinkerPatch() {
        // 我们可以从这里获得Tinker加载过程的信息
        if (BuildConfig.TINKER_ENABLE) {
            tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
            // 初始化TinkerPatch SDK
            TinkerPatch.init(tinkerApplicationLike)
                    .reflectPatchLibrary()
                    .setPatchRollbackOnScreenOff(true)
                    .setPatchRestartOnSrceenOff(true)
                    .setFetchPatchIntervalByHours(5);
            // 获取当前的补丁版本
            Log.d(TAG, "Current patch version is " + TinkerPatch.with().getPatchVersion());
            // fetchPatchUpdateAndPollWithInterval 与 fetchPatchUpdate(false)
            // 不同的是，会通过handler的方式去轮询
            TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
        }
        TinkerPatch.init(tinkerApplicationLike)
                //是否自动反射Library路径,无须手动加载补丁中的So文件 注意,调用在反射接口之后才能生效,你也可以使用Tinker的方式加载Library
                .reflectPatchLibrary()
                //向后台获取是否有补丁包更新,默认的访问间隔为3个小时，若参数为true,即每次调用都会真正的访问后台配置
                //你也可以在用户登录或者APP启动等一些关键路径，使用fetchPatchUpdate(true)强制检查更新
                .fetchPatchUpdate(false)
                //设置访问后台补丁包更新配置的时间间隔,默认为3个小时
                .setFetchPatchIntervalByHours(5)
                //向后台获得动态配置,默认的访问间隔为3个小时
                //若参数为true,即每次调用都会真正的访问后台配置
                .fetchDynamicConfig(new ConfigRequestCallback() {
                    @Override public void onSuccess(HashMap<String, String> hashMap) { }
                    @Override public void onFail(Exception e) { }
                }, false)
                //设置访问后台动态配置的时间间隔,默认为3个小时
                .setFetchDynamicConfigIntervalByHours(1)
                //设置当前渠道号,对于某些渠道我们可能会想屏蔽补丁功能；设置渠道后,我们就可以使用后台的条件控制渠道更新
                .setAppChannel("default")
                //屏蔽部分渠道的补丁功能
                .addIgnoreAppChannel("googleplay")
                //设置tinkerpatch平台的条件下发参数
                .setPatchCondition("test", "1")
                //设置补丁合成成功后,锁屏重启程序,默认是等应用自然重启
                .setPatchRestartOnSrceenOff(true)
                //我们可以通过ResultCallBack设置对合成后的回调,例如弹框什么
                //注意，setPatchResultCallback 的回调是运行在 intentService 的线程中
                .setPatchResultCallback(new ResultCallBack() {
                    @Override public void onPatchResult(PatchResult patchResult) {
                        Log.i(TAG, "onPatchResult callback here");
                    }
                })
                //设置收到后台回退要求时,锁屏清除补丁,默认是等主进程重启时自动清除
                .setPatchRollbackOnScreenOff(true)
                //我们可以通过RollbackCallBack设置对回退时的回调
                .setPatchRollBackCallback(new RollbackCallBack() {
                    @Override public void onPatchRollback() {
                        Log.i(TAG, "onPatchRollback callback here");
                    }
                });
    }
}
