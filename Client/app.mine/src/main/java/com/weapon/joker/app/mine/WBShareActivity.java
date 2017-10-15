package com.weapon.joker.app.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.weapon.joker.app.stub.share.ShareParams;
import com.weapon.joker.lib.middleware.utils.LogUtils;
import com.weapon.joker.lib.middleware.utils.ToastUtil;
import com.weapon.joker.lib.middleware.utils.Util;
import com.weapon.joker.lib.middleware.utils.share.WBShareManager;

import java.util.concurrent.ExecutionException;

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
    private ShareParams mShareParams = null;
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

        mShareParams = WBShareManager.getInstance().getShareParams();
        if (mShareParams == null) {
            LogUtils.logd(TAG + "shareparams cannot be null");
        }

        shareHandler = new WbShareHandler(this);
        shareHandler.registerApp();

        if (savedInstanceState != null) shareHandler.doResultIntent(getIntent(), this);
        sendMultiMessage(mShareParams);
    }

    //分享到微博
    private void sendMultiMessage(ShareParams params) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.textObject = getTextObj(params);
        ImageObject imageObject = getImageObj(params.getImgUrl());
        if (imageObject != null) {
            weiboMessage.imageObject = getImageObj(params.getImgUrl());
        }
        shareHandler.shareMessage(weiboMessage, false);
    }

    /**
     * 新浪微博对于移动端图片分享的大小限制为2M : http://sinaweibosdk.github.io/weibo_android_sdk/doc/com/sina/weibo/sdk/api/ImageObject.html#imageData
     * 但是实际是超过500kb 就出出现android.os.TransactionTooLargeException这个异常
     * 目前图片的最大尺寸参考网页版的最大尺寸 http://open.weibo.com/wiki/index.php/FAQ
     * author: yueyang
     **/
    private ImageObject getImageObj(String imageUrl) {
        ImageObject imageObject = new ImageObject();
        if (imageUrl.startsWith("http")) {
            byte[] imgBytes = Util.getNetBitmap(imageUrl);
            if (imgBytes != null) {
                if (imgBytes.length > 51200) {
                    Bitmap bitmap = getBitmapFromUrl(imageUrl);
                    imageObject.setImageObject(bitmap);
                } else {
                    imageObject.imageData = imgBytes;
                }
            }
        } else {
            imageObject.imagePath = imageUrl;
        }
        return imageObject;
    }

    private Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            return Glide.with(this)
                    .asBitmap()
                    .load(imageUrl)
                    .submit(440, 440)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
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

    private TextObject getTextObj(ShareParams params) {
        TextObject textObject = new TextObject();
        if (!TextUtils.isEmpty(params.getDescription())) textObject.text = params.getDescription();
        if (!TextUtils.isEmpty(params.getTitle())) textObject.title = params.getTitle();
        if (!TextUtils.isEmpty(params.getAppUrl())) textObject.actionUrl = params.getAppUrl();
        return textObject;
    }
}