package com.weapon.joker.app.mine.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.weapon.joker.app.mine.R;
import com.weapon.joker.lib.middleware.utils.Constants;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * author : yueyang
 * date : 2017/10/11
 * email : hi.yangyue1993@gmail.com
 */
public class QQAuthLoginActivity extends Activity {

    private WebView mWebView;
    public static String mUrl;
    public static final int QQ_AUTHORITY_REQUEST = 2001;
    private QQAuthLoginListener mQQAuthLoginListener = new QQAuthLoginListener() {
        @Override
        public void onComplete(String uid, String access_token) {
            // TODO: 2017/10/11 toLogin
        }

        @Override
        public void onError() {

        }
    };

    public static void launch(Activity activity) {
        if (activity == null) return;
        Intent intent = new Intent(activity, QQAuthLoginActivity.class);
        String encodeUrl, url = "";
        try {
            encodeUrl = URLEncoder.encode(Constants.QQ_REDIRECT_URL, "utf-8");
            url = Constants.QQ_URL_OAUTH2_AUTHORIZATION_CODE;
            url = url.replace("[YOUR_APPID]", Constants.QQ_APP_ID);
            url = url.replace("[YOUR_REDIRECT_URI]", encodeUrl);
            url = url.replace("[THE_SCOPE]", Constants.QQ_SCOPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        QQAuthLoginActivity.mUrl = url;
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        activity.startActivityForResult(intent, QQ_AUTHORITY_REQUEST);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_login);

        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        mWebView = findViewById(R.id.webview);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setSaveFormData(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new QQWebViewClient());
        mWebView.loadUrl(mUrl);
        mWebView.requestFocus();
    }

    private class QQWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(Constants.QQ_REDIRECT_URL)) {
                try {
                    handlerLoginUrl(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (isFinishing()) {
                return;
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (isFinishing()) {
                return;
            }
            mWebView.setVisibility(View.VISIBLE);
        }
    }

    private void handlerLoginUrl(String url) throws IOException {
        if (TextUtils.isEmpty(url))
            return;
        String newUrl = url.replace("?#", "=");
        newUrl = newUrl.replace("&", "=");
        String[] params = newUrl.split("=");
        String mAccessToken = params[2];//
        if (!TextUtils.isEmpty(mAccessToken))
            getOpenIdByAccessToken(mAccessToken);
        else
            mQQAuthLoginListener.onError();
    }

    private void getOpenIdByAccessToken(String token) {
        if (!TextUtils.isEmpty(token))
            throw new IllegalArgumentException("accessToken cannot be null");
        String url = Constants.QQ_URL_OAUTH2_OPENID;
        url = url.replace("[YOUR_ACCESS_TOKEN]", token);
        Uri.Builder urlBuilder = Uri.parse(url).buildUpon();
        final String realUrl = urlBuilder.build().toString();
        Request.Builder requestBuilder = new Request.Builder().url(realUrl).get();
        final Request request = requestBuilder.build();
        OkHttpClient okHttpClient = new OkHttpClient();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mQQAuthLoginListener.onError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String respStr = new String(body.bytes(), "utf-8");
                int start = respStr.indexOf("{");
                int end = respStr.indexOf("}") + 1;
                String substring = respStr.substring(start, end);
                // TODO: 2017/10/11 parse response
            }
        });
    }

    private interface QQAuthLoginListener {
        void onComplete(String uid, String access_token);

        void onError();
    }
}
