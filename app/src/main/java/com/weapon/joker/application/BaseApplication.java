package com.weapon.joker.application;

import android.app.Application;

import net.wequick.small.Small;

/**
 * Created by WeaponZhi on 2017/9/8.
 */

public class BaseApplication extends Application{

    public BaseApplication(){
        Small.preSetUp(this);
    }
}
