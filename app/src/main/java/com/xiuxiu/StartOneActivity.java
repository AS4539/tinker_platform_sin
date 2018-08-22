package com.xiuxiu;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.server.callback.ConfigRequestCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新增的页面
 * Created by ${weicheng} on 2018/8/22.
 */

public class StartOneActivity extends Activity implements ConfigRequestCallback {

    @BindView(R.id.cry_view)
    RecyclerView cryView;
    private OnLineAdapter onLineAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_one);
        ButterKnife.bind(this);
        initView();
        TinkerPatch.with().fetchDynamicConfig(this, false);
    }

    private void initView() {
        onLineAdapter = new OnLineAdapter(new ArrayList<OnLineBean>());
        cryView.setNestedScrollingEnabled(false);
        cryView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        cryView.setAdapter(onLineAdapter);
    }

    @Override
    public void onSuccess(HashMap<String, String> hashMap) {
        List<OnLineBean> onLineBeans = new ArrayList<>();
        for (String s : hashMap.keySet()) {
            onLineBeans.add(new OnLineBean(s,hashMap.get(s)));
        }
        onLineAdapter.setNewData(onLineBeans);
    }

    @Override
    public void onFail(Exception e) {

    }
}
