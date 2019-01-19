package com.weapon.joker.lib.middleware.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.jpush.im.android.api.JMessageClient;

/**
 * Util 常用工具类
 * author:张冠之
 * time: 2017/9/9 下午4:40
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class Util {

    private static final String TAG = Util.class.getSimpleName();

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

    /**
     * 将时间格式转换成时间戳
     *
     * @param date
     * @param style
     * @return
     */
    public static long getTime(String date, String style) {
        long time = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(style);
            time = sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 消息界面时间展示
     * 今年 {
     * 今天，则显示时分
     * 昨天，显示昨天
     * 前天，显示前天
     * 更早，显示月-日
     * } 往年 {
     * 显示 年/月/日
     * }
     * orz
     *
     * @return 返回时间
     */
    public static String getTimeForShow(long receiveTime) {
        String strDate = "";
        //今年
        Calendar now = Calendar.getInstance();
        int nowYear = now.get(Calendar.YEAR);

        //传进来的日期的年份
        now.setTimeInMillis(receiveTime);
        Date receiveDate = now.getTime();
        int receiveYear = now.get(Calendar.YEAR);

        /**
         * 判断是否是同一年
         */
        if (nowYear == receiveYear) {
            Calendar today = Calendar.getInstance();
            //获取今天过的毫秒数
            long todayMs = 1000 * (today.get(Calendar.HOUR_OF_DAY) * 3600 + today.get(Calendar.MINUTE) * 60 + today.get(Calendar.SECOND));
            //获取从1970到此刻的毫秒数
            long todayMsTotal = today.getTimeInMillis();

            if (todayMsTotal - receiveTime < todayMs) {
                //今天，使用 时:分
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                strDate = sdf.format(receiveDate);
            } else if (todayMsTotal - receiveTime < (todayMs + 24 * 3600 * 1000)) {
                //昨天
                strDate = "昨天";
            } else if (todayMsTotal - receiveTime < (todayMs + 24 * 3600 * 1000 * 2)) {
                //前天
                strDate = "前天";
            } else {
                //今年的更早，使用月-日
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                strDate = sdf.format(receiveDate);
            }
        } else {
            //往年，使用年-月-日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            strDate = sdf.format(receiveDate);
        }
        return strDate;
    }

    public static String getTimeForShowDetail(long receiveTime) {
        String strDate = "";
        //今年
        Calendar now = Calendar.getInstance();
        int nowYear = now.get(Calendar.YEAR);

        //传进来的日期的年份
        now.setTimeInMillis(receiveTime);
        Date receiveDate = now.getTime();
        int receiveYear = now.get(Calendar.YEAR);

        /**
         * 判断是否是同一年
         */
        if (nowYear == receiveYear) {
            Calendar today = Calendar.getInstance();
            //获取今天过的毫秒数
            long todayMs = 1000 * (today.get(Calendar.HOUR_OF_DAY) * 3600 + today.get(Calendar.MINUTE) * 60 + today.get(Calendar.SECOND));
            //获取从1970到此刻的毫秒数
            long todayMsTotal = today.getTimeInMillis();

            if (todayMsTotal - receiveTime < todayMs) {
                //今天，使用 时:分
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                strDate = sdf.format(receiveDate);
            } else if (todayMsTotal - receiveTime < (todayMs + 24 * 3600 * 1000)) {
                //昨天
                strDate = "昨天" + getStrTime(receiveTime, "HH:mm");
            } else if (todayMsTotal - receiveTime < (todayMs + 24 * 3600 * 1000 * 2)) {
                //前天
                strDate = "前天" + getStrTime(receiveTime, "HH:mm");
            } else {
                //今年的更早，使用月-日
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
                strDate = sdf.format(receiveDate);
            }
        } else {
            //往年，使用年-月-日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            strDate = sdf.format(receiveDate);
        }
        return strDate;
    }

    /**
     * 判断是否登录，如果未登录直接跳转到登录界面
     * @param activity
     * @return true-已登录
     *         false-未登录，并且跳转到登录界面
     */
    public static boolean checkHasLogin(Activity activity) {
        if (JMessageClient.getMyInfo() == null) {
//            PublicActivity.startActivity(activity, "com.weapon.joker.app.mine.login.LoginRegisterFragment");
            Intent intent = new Intent();
            intent.setClassName(activity, "com.weapon.joker.app.mine.login.LoginActivity");
            activity.startActivity(intent);
            return false;
        } else {
            return true;
        }
    }

    public static int dip2px(Context context, float dpValue) {
        if (context == null) {
            throw new IllegalArgumentException(TAG + ":convertPixelsToDp context cannot be null");
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
