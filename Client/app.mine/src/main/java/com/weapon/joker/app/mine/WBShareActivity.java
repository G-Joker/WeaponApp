package com.weapon.joker.app.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.weapon.joker.lib.middleware.utils.LogUtils;
import com.weapon.joker.lib.middleware.utils.ToastUtil;

/**
 * 添加微博客户端分享
 * author : yueyang
 * email : hi.yangyue1993@gmail.com
 * date : 2017/10/13
 */
public class WBShareActivity extends Activity implements WbShareCallback {

    public static final String KEY_SHARE_TYPE = "key_share_type";
    public static final int SHARE_CLIENT = 1;
    public static final int SHARE_ALL_IN_ONE = 2;
    private WbShareHandler shareHandler;
    private int mShareType = SHARE_CLIENT;
    private static final String TAG = WBShareActivity.class.getSimpleName() + "_";

    /**
     * 启动分享Activity
     * author: yueyang
     **/
    public static void launch(Context context) {
        Intent intent = new Intent(context, WBShareActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareHandler = new WbShareHandler(this);
        shareHandler.registerApp();

        if (savedInstanceState != null) {
            shareHandler.doResultIntent(getIntent(), this);
        }

        sendMultiMessage();
    }

    //分享到微博
    private void sendMultiMessage() {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.textObject = getTextObj();
        shareHandler.shareMessage(weiboMessage, false);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        shareHandler.doResultIntent(intent, this);
    }

    @Override
    public void onWbShareSuccess() {
        LogUtils.logd(TAG + "onWbShareSuccess");
        ToastUtil.show(R.string.share_success);
    }

    @Override
    public void onWbShareFail() {
        LogUtils.logd(TAG + "onWbShareFail");
        ToastUtil.show(R.string.share_error);
    }

    @Override
    public void onWbShareCancel() {
        LogUtils.logd(TAG + "onWbShareCancel");
        ToastUtil.show(R.string.share_cancel);
    }

    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = "sss";
        textObject.title = "xxxx";
        textObject.actionUrl = "http://github.com";
        return textObject;
    }
}
