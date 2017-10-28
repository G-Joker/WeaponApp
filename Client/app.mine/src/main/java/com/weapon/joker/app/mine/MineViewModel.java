package com.weapon.joker.app.mine;

import android.app.Activity;
import android.content.Intent;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Toast;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.umeng.analytics.MobclickAgent;
import com.weapon.joker.app.mine.about.AboutActivity;
import com.weapon.joker.app.stub.share.IShareListener;
import com.weapon.joker.app.stub.share.ShareParams;
import com.weapon.joker.app.stub.share.ShareType;
import com.weapon.joker.lib.middleware.PublicActivity;
import com.weapon.joker.lib.middleware.utils.LogUtils;
import com.weapon.joker.lib.middleware.utils.share.ShareView;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * class：   WeaponApp
 * author：  xiaweizi
 * date：    2017/9/17 11:01
 * e-mail:   1012126908@qq.com
 * desc:
 */
public class MineViewModel extends MineContact.ViewModel implements IShareListener, IUiListener {

    /**
     * 分享 view
     */
    private ShareView mShareView;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 是否登录
     */
    private boolean hasLogin = false;
    /**
     * 用户信息
     */
    private UserInfo mUserInfo;
    /**
     * 用户头像
     */
    @Bindable
    public File avatarFile;

    public void setAvatarFile(File avatarFile) {
        this.avatarFile = avatarFile;
        notifyPropertyChanged(com.weapon.joker.app.mine.BR.avatarFile);
    }

    /**
     * 设置用户名
     * @param userName
     */ public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(com.weapon.joker.app.mine.BR.userName);
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    /**
     * 设置是否登录
     *
     * @param hasLogin
     */
    public void setHasLogin(boolean hasLogin) {
        this.hasLogin = hasLogin;
        notifyPropertyChanged(com.weapon.joker.app.mine.BR.hasLogin);
    }

    @Bindable
    public boolean getHasLogin() {
        return hasLogin;
    }

    /**
     * 更新用户信息
     */
    public void updateUserInfo() {
        mUserInfo = JMessageClient.getMyInfo();
        if (mUserInfo == null) {
            // 未登录
            setUserName("我的");
            setHasLogin(false);
            setAvatarFile(null);
        } else {
            // 已经登录
            setUserName(mUserInfo.getDisplayName());
            setHasLogin(true);
            setAvatarFile(mUserInfo.getAvatarFile());
        }
    }

    /**
     * 初始化分享
     */
    public void initShare() {
        // 创建分享的参数
        ShareParams params = new ShareParams.Builder().setTitle(getContext().getResources().getString(R.string.share_name))
                .setDescription(getContext().getResources().getString(R.string.share_desc))
                .setAppUrl(getContext().getResources().getString(R.string.share_url))
                .setImgUrl(getContext().getResources().getString(R.string.share_img_url))
                .setResId(R.mipmap.round)
                .build();
        mShareView = new ShareView(getContext(), params, this);
    }

    /**
     * 点击分享按钮
     * @param view
     */
    public void shareOnClick(View view) {
        MobclickAgent.onEvent(getContext(), "share_click");
        mShareView.show();
    }

    public void loginOnClick(View view) {
        if (getHasLogin()) {
            // 如果已经登录则跳转到个人中心界面
            MobclickAgent.onEvent(getContext(), "mine_person_center");
            Intent intent = new Intent(getContext(), PublicActivity.class);
            intent.putExtra("user_name", mUserInfo.getUserName());
            PublicActivity.startActivity((Activity) getContext(), "com.weapon.joker.app.mine.person.PersonCenterFragment", intent);
        } else {
            // 如果没有登录则跳转到登录界面
//            Intent intent = new Intent(getContext(), LoginActivity.class);
//            getContext().startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) getContext())).toBundle());
            PublicActivity.startActivity((Activity) getContext(), "com.weapon.joker.app.mine.login.LoginRegisterFragment");
        }
    }

    /**
     *
     *  跳转到关于界面
     * author: yueyang
     **/
    public void jumpToAboutPage(View view) {
        AboutActivity.launch(getContext());
    }

    public IUiListener getIUiListener() {
        return this;
    }


    /*************************** 分享事件相关的回调 ***************************/
    @Override public void onShareSuccess(ShareType shareType, String s) {
        Toast.makeText(getContext(), shareType.toString() + ":\t分享成功", Toast.LENGTH_SHORT).show();
        LogUtils.i("Share result:\t" + "type:\t" + shareType.toString() + "desc:\t" + s);
    }

    @Override public void onShareFailed(ShareType shareType, String s) {
        Toast.makeText(getContext(), shareType.toString() + ":\t分享失败", Toast.LENGTH_SHORT).show();
        LogUtils.i("Share result:\t" + "type:\t" + shareType.toString() + "desc:\t" + s);

    }

    @Override public void onShareCancel(ShareType shareType) {
        Toast.makeText(getContext(), shareType.toString() + ":\t分享取消", Toast.LENGTH_SHORT).show();
        LogUtils.i("Share result:\t" + "type:\t" + shareType.toString());

    }

    @Override public void onComplete(Object o) {LogUtils.i("QQ Share result:\tonComplete!" + o.toString());}
    @Override public void onError(UiError uiError) {LogUtils.i("QQ Share result:\tonError!" + uiError.errorMessage);}
    @Override public void onCancel() {LogUtils.i("QQ Share result:\tonCancel!");}
    /************************************************************************/



}
