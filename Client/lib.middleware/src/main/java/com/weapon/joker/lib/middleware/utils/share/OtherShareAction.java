package com.weapon.joker.lib.middleware.utils.share;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

import com.weapon.joker.app.stub.share.IShareAction;
import com.weapon.joker.app.stub.share.IShareListener;
import com.weapon.joker.app.stub.share.ShareParams;
import com.weapon.joker.app.stub.share.ShareType;
import com.weapon.joker.lib.middleware.R;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.middleware.utils.share.OtherShareAction
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/09/30
 *     desc   : 其他类型的分享 action
 * </pre>
 */

public class OtherShareAction implements IShareAction {

    private Activity mActivity;

    public OtherShareAction(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void doShare(ShareParams shareParams, IShareListener iShareListener) {
        if (shareParams.getShareType() == ShareType.COPY) {
            addContentToClipboard(shareParams, iShareListener);
        } else {
            shareToSystem(shareParams, iShareListener);
        }
    }

    /**
     * 添加内容到剪贴板
     */
    private void addContentToClipboard(ShareParams shareParams, IShareListener shareListener) {
        ClipboardManager clip = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setPrimaryClip(ClipData.newPlainText(null, shareParams.toString()));
        shareListener.onShareSuccess(ShareType.COPY, mActivity.getString(R.string.add_clipboard_success));
    }

    /**
     * 分享到系统
     * @param shareParams
     * @param shareListener
     */
    private void shareToSystem(ShareParams shareParams, IShareListener shareListener) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, shareParams.getTitle());
        intent.putExtra(Intent.EXTRA_TEXT, shareParams.toString());
        intent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(intent, mActivity.getResources().getString(R.string.share_title));
        mActivity.startActivity(shareIntent);
        shareListener.onShareSuccess(ShareType.OTHER, "");
    }
}
