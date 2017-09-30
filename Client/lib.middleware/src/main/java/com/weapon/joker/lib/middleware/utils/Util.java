package com.weapon.joker.lib.middleware.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.util.ArrayMap;

import java.lang.reflect.Field;

/**
 * Util 常用工具类
 * author:张冠之
 * time: 2017/9/9 下午4:40
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class Util {


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static Activity getTopActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread")
                    .invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            ArrayMap activities = (ArrayMap) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Didn't find the running activity");
    }

}
