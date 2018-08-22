package com.xiuxiu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ${weicheng} on 2018/8/20.
 */

public class MyJPushBroader extends BroadcastReceiver {

    private String TAG = "MyJPushBroader";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        Log.e("JPushReceiver", "onReceive: "+extras.toString());
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction());
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
        }else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            System.out.println("收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            System.out.println("收到了通知");
            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            String s = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.e(TAG, "onReceive: "+s);
        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }
}
