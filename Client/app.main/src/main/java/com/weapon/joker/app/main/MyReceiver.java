package com.weapon.joker.app.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.weapon.joker.lib.middleware.utils.LogUtils;
import com.weapon.joker.lib.middleware.utils.NotificationUtil;
import com.weapon.joker.lib.mvvm.common.PublicActivity;
import com.weapon.joker.lib.net.GsonUtil;
import com.weapon.joker.lib.net.bean.PushNewsBean;
import com.weapon.joker.lib.net.data.PushNewsData;
import com.weapon.joker.lib.net.event.PushNewsEvent;

import net.wequick.small.Small;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.eventbus.EventBus;

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


    /**
     * 通用跳转的通知
     * 作为消息 tab 的数据源显示，不会弹出通知
     */
    private static final int NEWS_TYPE_COMMOM_JUMP = 1000;

    @Override
    public void onReceive(final Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            LogUtils.i("action:" + intent.getAction() + ", extras: " + printBundle(bundle));
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                LogUtils.i("Registration Id : " + regId);

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                LogUtils.i("Custom message : " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                dealCustomPush(context, bundle.getString(JPushInterface.EXTRA_MESSAGE));
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                LogUtils.i("Get push message!");
                int notification = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                LogUtils.i("Push message notification Id : " + notification);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                LogUtils.i("User has clicked the notification!");

                //打开自定义的Activity


            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
                LogUtils.i("Rich push callback : " + bundle.getString(JPushInterface.EXTRA_EXTRA));

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                LogUtils.i("connected state change to " + connected);
            } else {
                LogUtils.i("Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {
            LogUtils.e("Push has occurs some error! error message : " + e.getMessage());
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
            PushNewsBean pushNewsBean = GsonUtil.getInstance().fromJson(extra, PushNewsBean.class);
            if (pushNewsBean.type == NEWS_TYPE_COMMOM_JUMP) {
                // 将收到的消息保存到本地
                PushNewsData.getInstance().addPushNewsModel(context, pushNewsBean);
                Intent intent = new Intent(context, PublicActivity.class);
                intent.putExtra("fragment_name", "com.weapon.joker.app.message.post.PostFragment");
                Small.wrapIntent(intent);
                NotificationUtil.commonNotfication(intent, context, pushNewsBean.title, pushNewsBean.content, 12, R.mipmap.ic_launcher);
                EventBus.getDefault().post(new PushNewsEvent());
            } else {
                Intent intent = new Intent(context, MainActivity.class);
                Small.wrapIntent(intent);
                NotificationUtil.commonNotfication(intent, context, pushNewsBean.title, pushNewsBean.content, 12, R.mipmap.ic_launcher);
            }
        } catch (Exception e) {
            LogUtils.e("MyReceiver", "dealCustomPush error! desc: " + e.getMessage());
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
                    LogUtils.i("This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" + myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogUtils.i("Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
}
