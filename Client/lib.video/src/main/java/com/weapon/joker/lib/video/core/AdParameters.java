package com.weapon.joker.lib.video.core;


import com.weapon.joker.lib.video.constant.SDKConstant.AutoPlaySetting;

/**
 * @author: vision
 * @function: 广告SDK全局参数配置, 都用静态来保证统一性
 * @date: 16/7/1
 */
public final class AdParameters {

    //用来记录可自动播放的条件
    private static AutoPlaySetting currentSetting = AutoPlaySetting.AUTO_PLAY_3G_4G_WIFI; //默认都可以自动播放

    public static void setCurrentSetting(AutoPlaySetting setting) {
        currentSetting = setting;
    }

    public static AutoPlaySetting getCurrentSetting() {
        return currentSetting;
    }

    /**
     * 获取sdk当前版本号
     */
    public static String getAdSDKVersion() {
        return "1.0.0";
    }
}
