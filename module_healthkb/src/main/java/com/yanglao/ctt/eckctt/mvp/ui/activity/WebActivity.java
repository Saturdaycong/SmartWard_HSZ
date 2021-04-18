package com.yanglao.ctt.eckctt.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.yanglao.ctt.eckctt.R;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * ================================================
 * Description:
 * <p>
 *  11/27/2019 18:13

 * ================================================
 */
public class WebActivity extends BaseActivity {

    WebView actWebview;
    TextView actWebLoadingTv;
    private int mFrom;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_web; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    public static void launch(Context context, int from) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        actWebview=findViewById(R.id.act_webview);
        actWebLoadingTv=findViewById(R.id.act_web_loading_tv);

        mFrom = getIntent().getIntExtra("from", 1);
        initWebSetting();
        actWebview.setWebChromeClient(new WebChromeClient());
        actWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                actWebLoadingTv.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                showToast("url=="+url);
                super.onPageFinished(view, url);
            }

        });
        if (mFrom == 1) {
            actWebview.loadUrl("https://ylfw.aiqiangua.com:8443/ylfw/login.do?first_time_login=1&login_username=15875594090&login_password=123456");
        } else {
            actWebview.loadUrl("http://api.le-young.com:7830/jhealth/index");
        }
    }

    private void initWebSetting() {
        WebSettings settings = actWebview.getSettings();
        settings.setJavaScriptEnabled(true);
    }
}
