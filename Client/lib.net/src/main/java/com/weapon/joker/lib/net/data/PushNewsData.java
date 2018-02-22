package com.weapon.joker.lib.net.data;

import android.content.Context;
import android.text.TextUtils;

import com.weapon.joker.lib.middleware.utils.PreferencesUtils;
import com.weapon.joker.lib.net.GsonUtil;
import com.weapon.joker.lib.net.bean.CommonBean.PushNewsBean;
import com.weapon.joker.lib.net.model.PushNewsModel;

/**
 * class：   Client
 * author：  xiaweizi
 * date：    2017/10/17 21:17
 * e-mail:   1012126908@qq.com
 * desc:     推送消息数据存储
 */
public class PushNewsData {
    private static PushNewsData instance;
    private static final String PUSH_NEWS = "push_news";

    public static PushNewsData getInstance() {
        return instance == null ? (instance = new PushNewsData()) : instance;
    }

    /**
     * 保存推送下来的消息
     *
     * @param context
     */
    public void addPushNewsModel(Context context, PushNewsBean bean) {
        if (null == bean) {
            return;
        }
        PushNewsModel pushNewsData = getPushNewsData(context);
        pushNewsData.data.add(bean);
        PreferencesUtils.putString(context, PUSH_NEWS, GsonUtil.getInstance().toJson(pushNewsData));

    }

    /**
     * 获取本地推送下来的消息
     *
     * @param context
     * @return 保存在本地推送下来的消息
     */
    public PushNewsModel getPushNewsData(Context context) {
        PushNewsModel model;
        String pushNews = PreferencesUtils.getString(context, PUSH_NEWS, "");
        if (TextUtils.isEmpty(pushNews)) {
            model = new PushNewsModel();
        } else {
            model = GsonUtil.getInstance().fromJson(pushNews, PushNewsModel.class);
        }
        return model;
    }

    /**
     * 清除所有的本地推送消息
     *
     * @param context
     */
    public void clearAllPushNews(Context context) {
        PreferencesUtils.remove(context, PUSH_NEWS);
    }
}
