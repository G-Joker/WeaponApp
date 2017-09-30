package com.weapon.joker.app.stub.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.io.ByteArrayOutputStream;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.stub.share.WXShareAction
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/09/30
 *     desc   : 微信分享 aaction
 * </pre>
 */

public class WXShareAction implements IShareAction {

    private Context mContext;
    private static final int THUMB_SIZE    = 150;
    private IWXAPI mApi;

    private int mTargetScene = WX_ERROR; // 分享类别
    public static final int    WX_SESSION  = SendMessageToWX.Req.WXSceneSession; // 好友
    public static final int    WX_CIRCLE   = SendMessageToWX.Req.WXSceneTimeline; // 朋友圈
    public static final int    WX_FAVORITE = SendMessageToWX.Req.WXSceneFavorite; // 收藏

    public static final int    WX_ERROR    = -1; // 失败

    public WXShareAction(Context context, IWXAPI api) {
        this.mContext = context;
        this.mApi = api;
    }

    @Override
    public void  doShare(ShareParams shareParams, IShareListener listener) {
        dealTargetScene(shareParams);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareParams.getAppUrl();

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = shareParams.getTitle();
        msg.description = shareParams.getDescription();
        Bitmap bmp      = BitmapFactory.decodeResource(mContext.getResources(), shareParams.getResId());
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = mTargetScene;
        mApi.sendReq(req);
    }

    private void dealTargetScene(ShareParams shareParams) {
        if (shareParams.getShareType() == ShareType.WEIXIN) {
            mTargetScene = WX_SESSION;
        } else if (shareParams.getShareType() == ShareType.WEIXIN_CIRCLE) {
            mTargetScene = WX_CIRCLE;
        } else if (shareParams.getShareType() == ShareType.WEIXIN_FAVORITE) {
            mTargetScene = WX_FAVORITE;
        } else {
            mTargetScene = WX_SESSION;
        }
    }

    /**
     * Bitmap 转 byte
     */
    private   byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
