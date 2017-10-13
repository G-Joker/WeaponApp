package com.weapon.joker.lib.middleware;

import android.app.Application;
import android.content.Context;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.weapon.joker.lib.middleware.utils.AppContextHolder;
import com.weapon.joker.lib.middleware.utils.Constants;
import com.weapon.joker.lib.middleware.utils.LogUtils;

/**
 * BaseApplication 初始化
 * author:张冠之
 * time: 2017/10/11 下午4:22
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        LogUtils.logInit(true);
        WbSdk.install(this, new AuthInfo(
                this,
                Constants.WEIBO_APP_KEY,
                Constants.WEIBO_REDIRECT_URL,
                Constants.WEIBO_SCOPE));  //微博初始化
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppContextHolder.setContext(base); //设置全局的Context
    }
}
