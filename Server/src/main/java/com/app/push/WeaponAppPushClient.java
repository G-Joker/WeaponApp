package com.app.push;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.app.push.WeaponAppPushClient
 *     e-mail : 1012126908@qq.com
 *     time   : 2018/01/25
 *     desc   :
 * </pre>
 */

public class WeaponAppPushClient {

    public static void main(String[] args) {
        startPush();
    }

    private static void startPush() {
        String masterSecret = "ac5e8a94b40cfd7f4e3caf25";
        String appKey = "505a6c2fd4058902bbd76fb8";
        JPushClient jPushClient = new JPushClient(masterSecret, appKey);

        Map<String, String> data = new HashMap<String, String>();
        data.put("messageId", "12");
        data.put("time", "32131");
        data.put("title", "feixuan title");
        data.put("content", "http://www.baidu.com\n");
        data.put("imageUrl", "imageUrl");

        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias("WeaponApp"))
                .setMessage(Message.newBuilder()
                        .setMsgContent("content")
                        .setContentType("每日推荐")
                        .setTitle("title")
                        .addExtra("messageId", 12)
                        .addExtra("time", "32131")
                        .addExtra("title", "feixuan title1")
                        .addExtra("content", "http://www.baidu.com\n")
                        .addExtra("imageUrl", "imageUrl")
                        .build())
                .build();

        try {
            PushResult pushResult = jPushClient.sendPush(payload);
            System.out.println("success");
            System.out.println(pushResult.msg_id);
            System.out.println(pushResult.sendno);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
