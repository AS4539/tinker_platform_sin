package com.xiuxiu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.xiuxiu.utils.VersionUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.app_version_text)
    TextView appVersionText;
    @BindView(R.id.patch_version_text)
    TextView patchVersionText;
    @BindView(R.id.content_text)
    TextView contentText;
    @BindView(R.id.open_btn_one)
    Button openBtnOne;
    private Dialog dialog;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        contentText.setText("更新了新的补丁【 1c 】 \n 更新内容: \n  加固支持");
        contentText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        appVersionText.setText(VersionUtil.getVersionName(this));
        patchVersionText.setText("5");
    }

    public void initDialog() {
        dialog = new Dialog(this);
        View dialog_view = getLayoutInflater().inflate(R.layout.dialog_view, null);
        webView = dialog_view.findViewById(R.id.web_view);
        //覆盖webView默认通过系统浏览器打开网页的方式
        webView.setWebViewClient(new WebViewClient());
        webView.requestFocusFromTouch();
        //获取WebView类设置对象
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = this.getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);//允许弹窗
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//(不使用缓存)
        webView.loadUrl("file:///android_asset/index.html");
        dialog.setContentView(dialog_view);
        dialog.show();
    }

    @OnClick(R.id.open_btn_one)
    public void onViewClicked() {
        startActivity(new Intent(this,StartOneActivity.class));
    }

}
