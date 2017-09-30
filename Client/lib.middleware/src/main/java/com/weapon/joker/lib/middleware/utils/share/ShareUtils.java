package com.weapon.joker.lib.middleware.utils.share;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.weapon.joker.app.stub.share.IShareListener;
import com.weapon.joker.app.stub.share.ShareParams;
import com.weapon.joker.app.stub.share.ShareType;
import com.weapon.joker.lib.middleware.R;
import com.weapon.joker.lib.middleware.utils.Constants;
import com.weapon.joker.lib.middleware.utils.JLog;
import com.weapon.joker.wxapi.WXEntryActivity;

import java.util.ArrayList;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.middleware.utils.share.ShareUtils
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/09/25
 *     desc   : 分享工具
 * </pre>
 */

public class ShareUtils {

    private static ShareUtils  sInstance;
    private        Tencent     mTencent;
    private        Activity    mActivity;
    private IShareListener mShareListener;
    public static final String SHARE_PARAM = "param";

    public static ShareUtils getInstance(Activity activity, IShareListener listener) {

        if (sInstance == null) {
            synchronized (ShareUtils.class) {
                if (sInstance == null) {
                    sInstance = new ShareUtils(activity, listener);
                }
            }
        }

        return sInstance;
    }

    private ShareUtils(Activity activity, IShareListener listener) {
        this.mActivity = activity;
        this.mShareListener = listener;
        mTencent = Tencent.createInstance(Constants.QQ_APP_ID, activity.getApplicationContext());
    }


    /**
     * 分享到QQ空间
     */
    public void shareToQZone(ShareParams shareParams) {
        testParams(shareParams);
        try {
            Bundle params = new Bundle();
            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareParams.getTitle());
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareParams.getDescription());
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareParams.getAppUrl());
            ArrayList<String> value = new ArrayList<>();
            value.add(shareParams.getImgUrl());
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, value);
            mTencent.shareToQzone(mActivity, params, new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    mShareListener.onShareSuccess(ShareType.QQ_ZONE, o.toString());
                }

                @Override
                public void onError(UiError uiError) {
                    mShareListener.onShareFailed(ShareType.QQ_ZONE, "message:\t" + uiError.errorMessage + "\tdetail:\t" + uiError.errorDetail);
                }

                @Override
                public void onCancel() {
                    mShareListener.onShareCancel(ShareType.QQ_ZONE);
                }
            });
        } catch (Exception e) {
            JLog.i("Share:\t" + e.getMessage());
            mShareListener.onShareFailed(ShareType.QQ_ZONE, mActivity.getResources().getString(R.string.share_error_toast));
        }
    }

    /**
     * 分享给QQ好友
     */
    public void shareToQQ(ShareParams shareParams) {
        testParams(shareParams);
        try {
            Bundle params = new Bundle();
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, shareParams.getTitle());
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareParams.getDescription());
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareParams.getAppUrl());
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareParams.getImgUrl());
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "WeaponApp");
            mTencent.shareToQQ(mActivity, params, new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    mShareListener.onShareSuccess(ShareType.QQ, o.toString());
                }

                @Override
                public void onError(UiError uiError) {
                    mShareListener.onShareFailed(ShareType.QQ, "message:\t" + uiError.errorMessage + "\tdetail:\t" + uiError.errorDetail);
                }

                @Override
                public void onCancel() {
                    mShareListener.onShareCancel(ShareType.QQ);
                }
            });
        } catch (Exception e) {
            JLog.i("Share:\t" + e.getMessage());
            mShareListener.onShareFailed(ShareType.QQ, mActivity.getResources().getString(R.string.share_error_toast));
        }
    }

    /**
     * 分享给微信好友
     */
    public void shareToWX(ShareParams shareParams) {
        testParams(shareParams);
        try {
            Intent wxIntent = new Intent(mActivity, WXEntryActivity.class);
            wxIntent.putExtra(WXEntryActivity.SHARE_TYPE, WXEntryActivity.WX_SESSION);
            wxIntent.putExtra(SHARE_PARAM, shareParams);
            mActivity.startActivity(wxIntent);
        } catch (Exception e) {
            JLog.i("Share:\t" + e.getMessage());
            mShareListener.onShareFailed(ShareType.WEIXIN, mActivity.getResources().getString(R.string.share_error_toast));
        }
    }


    /**
     * 分享到朋友圈
     */
    public void shareToWXFriendCircle(ShareParams shareParams) {
        testParams(shareParams);
        try {
            Intent wxIntent = new Intent(mActivity, WXEntryActivity.class);
            wxIntent.putExtra(WXEntryActivity.SHARE_TYPE, WXEntryActivity.WX_CIRCLE);
            wxIntent.putExtra(SHARE_PARAM, shareParams);
            mActivity.startActivity(wxIntent);
        } catch (Exception e) {
            JLog.i("Share:\t" + e.getMessage());
            mShareListener.onShareFailed(ShareType.WEIXIN_CIRCLE, mActivity.getResources().getString(R.string.share_error_toast));
        }
    }

    /**
     * 分享到收藏
     */
    public void shareToWXFavorite(ShareParams shareParams) {
        testParams(shareParams);
        try {
            Intent wxIntent = new Intent(mActivity, WXEntryActivity.class);
            wxIntent.putExtra(WXEntryActivity.SHARE_TYPE, WXEntryActivity.WX_FAVORITE);
            wxIntent.putExtra(SHARE_PARAM, shareParams);
            mActivity.startActivity(wxIntent);
        } catch (Exception e) {
            JLog.i("Share:\t" + e.getMessage());
            mShareListener.onShareFailed(ShareType.WEIXIN_FAVORITE, mActivity.getResources().getString(R.string.share_error_toast));
        }
    }

    /**
     * 添加内容到剪贴板
     */
    public void addContentToClipboard (ShareParams shareParams) {
        testParams(shareParams);
        ClipboardManager clip = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setPrimaryClip(ClipData.newPlainText(null, shareParams.toString()));
        mShareListener.onShareSuccess(ShareType.COPY, mActivity.getString(R.string.add_clipboard_success));
    }

    public void shareToSystem(ShareParams shareParams) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, shareParams.getTitle());
        intent.putExtra(Intent.EXTRA_TEXT, shareParams.toString());
        intent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(intent, mActivity.getResources().getString(R.string.share_title));
        mActivity.startActivity(shareIntent);
        mShareListener.onShareSuccess(ShareType.OTHER, "");
    }

    private void testParams(ShareParams params) {
        if (params == null) {
            throw new RuntimeException("shareParams cannot be null!");
        }
    }

}
