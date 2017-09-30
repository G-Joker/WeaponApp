package com.weapon.joker.lib.middleware.utils.share;

import android.app.Activity;
import android.content.Intent;

import com.weapon.joker.app.stub.share.IShareListener;
import com.weapon.joker.app.stub.share.ShareParams;
import com.weapon.joker.lib.middleware.R;
import com.weapon.joker.lib.middleware.utils.JLog;
import com.weapon.joker.wxapi.WXEntryActivity;

import static com.weapon.joker.wxapi.WXEntryActivity.SHARE_PARAM;

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

    private static ShareUtils     sInstance;
    private        Activity       mActivity;
    private        IShareListener mShareListener;
    private        QQShareAction  mQQShareAction;
    private OtherShareAction mOtherShareAction;

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
        mQQShareAction = new QQShareAction(activity);
        mOtherShareAction = new OtherShareAction(activity);
    }


    /**
     * 分享到QQ or 空间
     */
    public void shareToQQ(ShareParams shareParams) {
        testParams(shareParams);
        mQQShareAction.doShare(shareParams, mShareListener);
    }


    /**
     * 分享给微信好友 or 微信朋友圈 or 收藏
     */
    public void shareToWX(ShareParams shareParams) {
        testParams(shareParams);
        try {
            Intent wxIntent = new Intent(mActivity, WXEntryActivity.class);
            wxIntent.putExtra(SHARE_PARAM, shareParams);
            mActivity.startActivity(wxIntent);
        } catch (Exception e) {
            JLog.i("Share:\t" + e.getMessage());
            mShareListener.onShareFailed(shareParams.getShareType(), mActivity.getResources().getString(R.string.share_error_toast));
        }
    }



    public void shareToOther(ShareParams shareParams) {
        testParams(shareParams);
        mOtherShareAction.doShare(shareParams, mShareListener);
    }

    private void testParams(ShareParams params) {
        if (params == null) {
            throw new RuntimeException("shareParams cannot be null!");
        }
    }

}
