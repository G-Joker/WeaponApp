package com.weapon.joker.lib.middleware;

import android.app.Application;

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
//        WbSdk.install(this, new AuthInfo(
//                this,
//                Constants.WEIBO_APP_KEY,
//                Constants.WEIBO_REDIRECT_URL,
//                Constants.WEIBO_SCOPE));  //微博初始化
    }

}
