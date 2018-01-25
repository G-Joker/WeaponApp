package com.app.push;

import com.app.push.model.RecommendDailyModel;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.app.push.PushUtil
 *     e-mail : 1012126908@qq.com
 *     time   : 2018/01/25
 *     desc   :
 * </pre>
 */

public class PushUtil {

    private static PushUtil mUtil = null;
    private static final String masterSecret = "331f43a91b168f6b625daeec";
    private static final String appKey = "505a6c2fd4058902bbd76fb8";
    private final JPushClient mJPushClient;

    private PushUtil() {
        mJPushClient = new JPushClient(masterSecret, appKey);
    }

    public static PushUtil getInstance() {
        if (mUtil == null) {
            synchronized (PushUtil.class) {
                if (mUtil == null) {
                    mUtil = new PushUtil();
                }
            }
        }
        return mUtil;
    }

    /**
     * 每日推送
     * @param audience 推送对象
     * @param model 推送数据
     */
    public void pushRecommendDaily(Audience audience, RecommendDailyModel model) {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(audience)
                .setMessage(Message.newBuilder()
                        .setMsgContent("content")
                        .setContentType("每日推荐")
                        .addExtra("time", model.time)
                        .addExtra("title", model.title)
                        .addExtra("content", model.content)
                        .addExtra("imageUrl", model.imageUrl)
                        .addExtra("url", model.url)
                        .build())
                .build();

        try {
            PushResult pushResult = mJPushClient.sendPush(payload);
            System.out.println(pushResult.sendno);
            System.out.println(pushResult.msg_id);
            System.out.println(pushResult.statusCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
