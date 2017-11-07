package com.weapon.joker.lib.middleware.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.middleware.utils.NotificationUtil
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/09/22
 *     desc   : joker 通知栏工具
 * </pre>
 */

public class NotificationUtil {

    /**
     * 普通跳转
     *
     * @param intent   跳转的 intent
     * @param content  通知 content
     * @param notifyId 通知 id
     * @param iconId   通知图标 资源id
     */
    public static void commonNotfication(Intent intent, Context context,String title, String content, int notifyId, int iconId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent        contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder       = new Notification.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setContentIntent(contentIntent);

        Notification notification = builder.build();
        notification.icon = iconId;
        notification.defaults = Notification.DEFAULT_LIGHTS;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.when = System.currentTimeMillis();
        notification.tickerText = content;

        notificationManager.notify(notifyId, notification);
    }

}
