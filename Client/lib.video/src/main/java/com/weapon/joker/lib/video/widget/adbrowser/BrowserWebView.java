package com.weapon.joker.lib.video.widget.adbrowser;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;

/**
 * @function 自定义WebView，设置一些能用的参数
 */
public class BrowserWebView extends WebView {

    public BrowserWebView(Context context) {
        super(context);
        init();
    }

    private void init() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);

        webSettings.setPluginState(PluginState.ON);

        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        setInitialScale(1);
    }
}
