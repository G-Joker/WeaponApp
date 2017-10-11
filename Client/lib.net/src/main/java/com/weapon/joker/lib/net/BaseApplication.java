package com.weapon.joker.lib.net;

import android.app.Application;
import android.content.Context;

/**
 * Created by WeaponZhi on 2017/10/11.
 */

public class BaseApplication extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
