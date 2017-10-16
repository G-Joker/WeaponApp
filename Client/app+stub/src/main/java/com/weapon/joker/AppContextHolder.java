package com.weapon.joker;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * 全局的Context
 * author : yueyang
 * email : hi.yangyue1993@gmail.com
 * date : 2017/10/13
 */
public class AppContextHolder {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static void setContext(Context context) {
        AppContextHolder.context = context;
    }

    public static Context getContext() {
        return context;
    }
}
