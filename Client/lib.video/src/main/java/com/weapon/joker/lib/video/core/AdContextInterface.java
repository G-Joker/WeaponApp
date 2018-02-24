package com.weapon.joker.lib.video.core;

/**
 * @author: vision
 * @function: 最终通知应用层广告是否成功
 * @date: 16/6/1
 */
public interface AdContextInterface {

    void onAdSuccess();

    void onAdFailed();

    void onClickVideo(String url);
}
