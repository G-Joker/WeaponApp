package com.weapon.joker.lib.middleware.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.weapon.joker.AppContextHolder;

/**
 * Toast工具类
 * author : yueyang
 * email : hi.yangyue1993@gmail.com
 * date : 2017/10/13
 */
public class ToastUtil {

    private static final String TAG = ToastUtil.class.getSimpleName() + "_";

    public static void show(String message) {
        printErrorIfNeeded(message);
        Toast.makeText(AppContextHolder.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void show(int resId) {
        Context context = AppContextHolder.getContext();
        String message = context.getString(resId);
        printErrorIfNeeded(message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String message) {
        Context context = AppContextHolder.getContext();
        printErrorIfNeeded(message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(int resId) {
        Context context = AppContextHolder.getContext();
        String message = context.getString(resId);
        printErrorIfNeeded(message);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    private static void printErrorIfNeeded(String message) {
        if (TextUtils.isEmpty(message)) {
            LogUtils.logd(TAG + "message cannot be null");
        }
    }


}
