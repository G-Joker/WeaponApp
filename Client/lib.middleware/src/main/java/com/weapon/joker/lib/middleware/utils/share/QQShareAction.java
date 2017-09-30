package com.weapon.joker.lib.middleware.utils.share;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.weapon.joker.app.stub.share.IShareAction;
import com.weapon.joker.app.stub.share.IShareListener;
import com.weapon.joker.app.stub.share.ShareParams;
import com.weapon.joker.app.stub.share.ShareType;
import com.weapon.joker.lib.middleware.R;
import com.weapon.joker.lib.middleware.utils.Constants;
import com.weapon.joker.lib.middleware.utils.JLog;

import java.util.ArrayList;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.middleware.utils.share.QQShareAction
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/09/30
 *     desc   : QQ 分享 action
 * </pre>
 */

public class QQShareAction implements IShareAction {

    private Activity mActivity;
    private Tencent  mTencent;

    public QQShareAction(Activity activity) {
        this.mActivity = activity;
        mTencent = Tencent.createInstance(Constants.QQ_APP_ID, activity.getApplicationContext());
    }

    @Override
    public void doShare(ShareParams shareParams, IShareListener iShareListener) {
        try {
            if (shareParams.getShareType() == ShareType.QQ) {
                shareToQQ(shareParams, iShareListener);
            } else {
                shareToQZone(shareParams, iShareListener);
            }
        } catch (Exception e) {
            JLog.i("Share:\t" + e.getMessage());
            iShareListener.onShareFailed(shareParams.getShareType(), mActivity.getResources().getString(R.string.share_error_toast));
        }
    }

    /**
     * @param shareParams
     * @param shareListener
     */
    private void shareToQQ(final ShareParams shareParams, final IShareListener shareListener) {
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
                shareListener.onShareSuccess(shareParams.getShareType(), o.toString());
            }

            @Override
            public void onError(UiError uiError) {
                shareListener.onShareFailed(shareParams.getShareType(),
                                            "message:\t" + uiError.errorMessage + "\tdetail:\t" + uiError.errorDetail);
            }

            @Override
            public void onCancel() {
                shareListener.onShareCancel(shareParams.getShareType());
            }
        });
    }

    /**
     * 分享到QQ空间
     */
    private void shareToQZone(final ShareParams shareParams, final IShareListener shareListener) {
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
                shareListener.onShareSuccess(shareParams.getShareType(), o.toString());
            }

            @Override
            public void onError(UiError uiError) {
                shareListener.onShareFailed(shareParams.getShareType(),
                                            "message:\t" + uiError.errorMessage + "\tdetail:\t" + uiError.errorDetail);
            }

            @Override
            public void onCancel() {
                shareListener.onShareCancel(shareParams.getShareType());
            }
        });

    }
}
