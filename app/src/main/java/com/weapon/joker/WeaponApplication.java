package com.weapon.joker;

import android.app.Application;

import net.wequick.small.Small;

/**
 * BaseApplication 程序入口，做初始化工作，并对整个应用提供上下文环境
 * author:张冠之
 * time: 2017/9/9 下午3:13
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class WeaponApplication extends Application{
    //单例模式
    private static WeaponApplication sApplication = null;

    public WeaponApplication(){
        //初始化 Small
        Small.preSetUp(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }

    public static WeaponApplication getInstance(){
        return sApplication;
    }
}
