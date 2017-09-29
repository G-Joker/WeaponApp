package com.weapon.joker.wxapi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.weapon.joker.app.stub.R;
import com.weapon.joker.app.stub.share.ShareParams;

import java.io.ByteArrayOutputStream;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : WXEntryActivity
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/09/28
 *     desc   : 微信分享界面
 * </pre>
 */

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private static final String WX_APP_ID = "wxb2d4af31b04e5d5a";

    public static final String SHARE_TYPE  = "share_type";
    public static final int    WX_SESSION  = SendMessageToWX.Req.WXSceneSession; // 好友
    public static final int    WX_CIRCLE   = SendMessageToWX.Req.WXSceneTimeline; // 朋友圈
    public static final int    WX_FAVORITE = SendMessageToWX.Req.WXSceneFavorite; // 收藏
    public static final int    WX_ERROR    = -1; // 失败
    private static final int THUMB_SIZE    = 150;


    private int mTargetScene = WX_ERROR; // 分享类别
    private ShareParams mParams;

    private IWXAPI mApi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = WXAPIFactory.createWXAPI(this, WX_APP_ID);
        setContentView(R.layout.activity_wxshare);
        Intent intent = getIntent();
        if (intent != null) {
            mTargetScene = getIntent().getIntExtra(SHARE_TYPE, WX_ERROR);
            mParams = (ShareParams) getIntent().getSerializableExtra("param");
        }
        if (mTargetScene != WX_ERROR && mParams != null) {
            Log.i("xwz--->", "onCreate: " + mParams.toString());
            shareToSession();
            try {
                mApi.handleIntent(getIntent(), this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mApi.handleIntent(intent, this);
    }

    private void shareToSession() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = mParams.getAppUrl();

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = mParams.getTitle();
        msg.description = mParams.getDescription();
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), mParams.getResId());
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = mTargetScene;
        mApi.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.i("xwz--->", "onReq--------");
    }


    @Override
    public void onResp(BaseResp resp) {
        int result = 0;

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }

        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        this.finish();
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
}
