package com.weapon.joker.lib.video.core;

import android.content.Context;
import android.view.ViewGroup;

import com.weapon.joker.lib.video.core.display.DisplayAdContext;
import com.weapon.joker.lib.video.db.realm.RealmManager;


/**
 * Created by qndroid on 16/11/1.
 *
 * @function 用来为调用者创建所有支持类型的广告
 */

public final class AdSDKManager {

    //SDK全局Context
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        //初始化SDK的时候，初始化Realm数据库
        RealmManager.init(mContext);
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 创建开机图广告
     *
     * @param parentContainer
     * @param appListener
     */
    public static void createDisplayAd(ViewGroup parentContainer, DisplayAdContext.DisplayAdAppListener appListener) {
        DisplayAdContext adContext = new DisplayAdContext(parentContainer);
        adContext.setAdAppListener(appListener);
    }
}
