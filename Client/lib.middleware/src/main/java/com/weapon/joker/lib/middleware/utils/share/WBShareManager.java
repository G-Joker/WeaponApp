package com.weapon.joker.lib.middleware.utils.share;

import com.weapon.joker.app.stub.share.ShareParams;

/**
 * 微博分享Manager，用于将ShareParams数据的转换
 * author : yueyang
 * email : hi.yangyue1993@gmail.com
 * date : 2017/10/13
 */
public class WBShareManager {


    private ShareParams mShareParams;

    private static class InstanceHolder{
        private static final WBShareManager instance = new WBShareManager();
    }

    public static WBShareManager getInstance(){
        return InstanceHolder.instance;
    }

    public void setShareParams(ShareParams shareParams) {
        mShareParams = shareParams;
    }

    public ShareParams getShareParams() {
        return mShareParams;
    }
}