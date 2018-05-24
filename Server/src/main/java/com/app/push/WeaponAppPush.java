package com.app.push;

import com.app.push.model.RecommendDailyModel;
import com.app.server.util.Util;

import cn.jpush.api.push.model.audience.Audience;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.app.push.WeaponAppPush
 *     e-mail : 1012126908@qq.com
 *     time   : 2018/01/25
 *     desc   : 推送后台
 * </pre>
 */

public class WeaponAppPush {

    public static void main(String[] args) {
        RecommendDailyModel model = new RecommendDailyModel();
        model.content = "content";
        model.title = "title";
        model.time  = Util.getStrTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm");
        model.url = "https://www.jiguang.cn/push";
        model.imageUrl = "http://avatar.csdn.net/C/A/8/1_ningmeng718.jpg";
        PushUtil.getInstance().pushRecommendDaily(Audience.alias("WeaponApp"), model);
    }

}
