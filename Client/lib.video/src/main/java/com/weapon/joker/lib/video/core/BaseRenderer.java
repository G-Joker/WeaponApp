package com.weapon.joker.lib.video.core;

import android.content.Context;
import android.view.ViewGroup;


/**
 * Created by qndroid on 16/10/27.
 *
 * @function 所有广告类型父类， 目前有開機图加轮播广告
 */

public class BaseRenderer {

    protected Context mContext;

    /**
     * UI
     */
    protected ViewGroup mParentView; //广告要加载到的父容器

    protected void initAdView() {
    }

    /**
     * 展示广告
     */
    public void onShow(String uri) {

    }

    /**
     * 销毁广告
     */
    public void onDispose() {
    }
}
