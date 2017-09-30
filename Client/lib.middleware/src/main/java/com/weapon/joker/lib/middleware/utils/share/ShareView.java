package com.weapon.joker.lib.middleware.utils.share;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.weapon.joker.app.stub.share.IShareListener;
import com.weapon.joker.app.stub.share.ShareParams;
import com.weapon.joker.lib.middleware.R;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.middleware.view.ShareView
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/09/25
 *     desc   : 分享 view
 * </pre>
 */

public class ShareView {

    private BottomSheetDialog mDialog;
    private Context mContext;
    private Activity mActivity;
    private ShareUtils mShareUtils;

    private LinearLayout mLlShareQQ;
    private LinearLayout mLlShareZone;
    private LinearLayout mLlShareWechat;
    private LinearLayout mLlShareFriendCircle;
    private LinearLayout mLlShareWeibo;
    private LinearLayout mLlShareWxFavorite;
    private LinearLayout mLlShareCopyLink;
    private LinearLayout mLlShareOther;
    private Button mBtCancel;

    private ShareParams    mParams;
    private IShareListener mListener;


    public ShareView(Context context, ShareParams shareParams, IShareListener listener) {
        if (!(context instanceof Activity)) {
            throw new RuntimeException("context must is activity!");
        }
        if (listener == null) {
            throw new RuntimeException("listener should not be null!");
        }
        mContext = context;
        mActivity = (Activity) context;
        mParams = shareParams;
        mListener = listener;
        mShareUtils = ShareUtils.getInstance(mActivity, mListener);
        initView(context);
    }

    private void initView(Context context) {
        mDialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.view_share, null);
        mLlShareQQ = view.findViewById(R.id.ll_share_qq);
        mLlShareZone = view.findViewById(R.id.ll_share_zone);
        mLlShareWechat = view.findViewById(R.id.ll_share_we_chat);
        mLlShareFriendCircle = view.findViewById(R.id.ll_share_friend_circle);
        mLlShareWeibo = view.findViewById(R.id.ll_share_weibo);
        mLlShareWxFavorite = view.findViewById(R.id.ll_share_wx_favorite);
        mLlShareCopyLink = view.findViewById(R.id.ll_share_copy_link);
        mLlShareOther = view.findViewById(R.id.ll_share_other);
        mBtCancel = view.findViewById(R.id.bt_share_cancel);
        initListener();
        mDialog.setContentView(view);
    }

    /**
     * 初始化 listener
     */
    private void initListener() {
        mLlShareQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareUtils.shareToQQ(mParams);
                mDialog.cancel();
            }
        });
        mLlShareZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareUtils.shareToQZone(mParams);
                mDialog.cancel();
            }
        });
        mLlShareWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareUtils.shareToWX(mParams);
                mDialog.cancel();
            }
        });
        mLlShareFriendCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareUtils.shareToWXFriendCircle(mParams);
                mDialog.cancel();
            }
        });
        mLlShareWxFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareUtils.shareToWXFavorite(mParams);
                mDialog.cancel();
            }
        });
        mLlShareWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "微博", Toast.LENGTH_SHORT).show();
                mDialog.cancel();
            }
        });
        mLlShareCopyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareUtils.addContentToClipboard(mParams);
                mDialog.cancel();
            }
        });
        mLlShareOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareUtils.shareToSystem(mParams);
                mDialog.cancel();
            }
        });
        mBtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "取消分享", Toast.LENGTH_SHORT).show();
                mDialog.cancel();
            }
        });
    }

    /**
     * @return BottomSheetDialog 对象
     */
    public BottomSheetDialog getBottomSheetDialog() {
        return mDialog;
    }

    /**
     * 显示 dialog
     */
    public void show() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    /**
     * 设置点击空白处是否可以关闭界面
     * @param cancelable
     */
    public void setCancelable(boolean cancelable) {
        if (mDialog != null) {
            mDialog.setCancelable(cancelable);
        }
    }


}
