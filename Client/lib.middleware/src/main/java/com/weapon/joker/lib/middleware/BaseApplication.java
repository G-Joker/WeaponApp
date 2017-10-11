package com.weapon.joker.lib.middleware;

import android.app.Application;

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
    }
}
