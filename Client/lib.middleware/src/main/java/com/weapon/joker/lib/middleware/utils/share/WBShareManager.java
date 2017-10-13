package com.weapon.joker.lib.middleware.utils.share;

/**
 * 微博分享Manager，用于将ShareParams数据的转换
 * author : yueyang
 * email : hi.yangyue1993@gmail.com
 * date : 2017/10/13
 */
public class WBShareManager {


    private static class InstanceHolder{
        private static final WBShareManager instance = new WBShareManager();
    }

    public static WBShareManager getInstance(){
        return InstanceHolder.instance;
    }

}
