package com.weapon.joker.lib.middleware.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.StrictMode;
import android.util.ArrayMap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Util 常用工具类
 * author:张冠之
 * time: 2017/9/9 下午4:40
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class Util {


    @TargetApi (Build.VERSION_CODES.KITKAT)
    public static Activity getTopActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
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

    public static byte[] getNetBitmap(String url) {
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            /**
             *  StrictMode用来检测两种问题:
             *  1.线程策略 (主线程中耗时操作的检测,比如IO读写)
             *  2.VM策略 (内存泄漏)
             */
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            in = new BufferedInputStream(new URL(url).openStream(), 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 1024);
            copy(in, out);
            out.flush();
            return dataStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] b = new byte[1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    /**
     * 将时间戳转为字符串
     *
     * @param time  时间戳
     * @param style 时间格式 (类似于"yyyy-MM-dd HH:mm")
     * @return
     */
    public static String getStrTime(long time, String style) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(style);
        re_StrTime = sdf.format(new Date(time));
        return re_StrTime;

    }
}
