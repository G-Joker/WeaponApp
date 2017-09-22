package com.weapon.joker.app.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.weapon.joker.lib.middleware.utils.JLog;
import com.weapon.joker.lib.middleware.utils.NotificationUtil;

import net.wequick.small.Small;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.main.MyReceiver
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/09/20
 *     desc   : 极光推送自定义Receiver
 * </pre>
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            JLog.i("action:" + intent.getAction() + ", extras: " + printBundle(bundle));
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                JLog.i("Registration Id : " + regId);

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                JLog.i("Custom message : " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                dealCustomPush(context, bundle.getString(JPushInterface.EXTRA_MESSAGE));
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                JLog.i("Get push message!");
                int notification = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                JLog.i("Push message notification Id : " + notification);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                JLog.i("User has clicked the notification!");

                //打开自定义的Activity


            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
                JLog.i("Rich push callback : " + bundle.getString(JPushInterface.EXTRA_EXTRA));

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                JLog.i("connected state change to " + connected);
            } else {
                JLog.i("Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {
            JLog.e("Push has occurs some error! error message : " + e.getMessage());
        }

    }

    /**
     * 处理自定义推送消息
     *
     * @param extra 自定义推送消息
     */
    private void dealCustomPush(Context context, String extra) {
        if (TextUtils.isEmpty(extra)) {
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(extra);
            String     title      = jsonObject.getString("title");
            Intent     intent     = new Intent(context, MainActivity.class);
            Small.wrapIntent(intent);
            NotificationUtil.commonNotfication(intent, context, title, 12, R.mipmap.ic_launcher);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 打印所有的 bundle 信息
     *
     * @param bundle
     * @return
     */
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    JLog.i("This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject       json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it   = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                  myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    JLog.i("Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
}
