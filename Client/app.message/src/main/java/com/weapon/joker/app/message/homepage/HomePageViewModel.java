package com.weapon.joker.app.message.homepage;

import android.app.Activity;
import android.content.Intent;
import android.databinding.Bindable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.lib.middleware.PublicActivity;
import com.weapon.joker.lib.middleware.utils.AlertDialogFactory;

import java.io.File;

import cn.jpush.im.android.api.model.UserInfo;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.homepage.HomePageViewModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/27
 *     desc   :
 * </pre>
 */

public class HomePageViewModel extends HomePageContact.ViewModel {

    @Bindable
    public String homePage =
            "homehomepahomehomepagehomepagehomepagehomepagepagehomehomepagehomepagehomepagehomepagepagehomehomepagehomepagehomepagehomepagepagegehomepagehom" +
            "homehomepahomehomepagehomepagehomepagehomepagepagehomehomepagehomepagehomepagehomepagepagehomehomepagehomepagehomepagehomepagepagegehomepageh" +
            "homehomepahomehomepagehomepagehomepagehomepagepagehomehomepagehomepagehomepagehomepagepagehomehomepagehomepagehomepagehomepagepagegehomepagehomepagehomepagepageomepagehomepagepageepagehomepagepage";

    /**
     * 显示名
     */
    @Bindable
    public String displayName;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 个性签名
     */
    @Bindable
    public String signature;
    /**
     * 用户头像
     */
    @Bindable
    public File avatarFile;
    private AlertDialog mLoadingDialog;

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        notifyPropertyChanged(BR.displayName);
    }

    public void setAvatarFile(File avatarFile) {
        this.avatarFile = avatarFile;
        notifyPropertyChanged(BR.avatarFile);
    }

    public void setSignature(String signature) {
        this.signature = signature;
        notifyPropertyChanged(BR.singleModel);
    }

    @Override
    public void attachView(HomePageContact.View view) {
        super.attachView(view);
        mLoadingDialog = AlertDialogFactory.createLoadingDialog(getContext(), "正在获取用户信息");
    }

    /**
     * 初始化
     *
     * @param userName
     */
    public void init(String userName) {
        mLoadingDialog.show();
        this.userName = userName;
        getModel().getUserInfo(userName);
    }

    @Override
    void setUserInfo(UserInfo userInfo) {
        // 关闭对话框
        dismissDialog();
        // 显示用户展示名称
        setDisplayName(userInfo.getDisplayName());
        // 设置用户头像
        setAvatarFile(userInfo.getAvatarFile());
        // 设置个性签名
        setSignature(userInfo.getSignature());
    }

    @Override
    void setError(String desc) {
        dismissDialog();
        Toast.makeText(getContext(), "获取用户信息失败!\t" + desc, Toast.LENGTH_SHORT).show();
    }

    /**
     * 关闭对话框
     */
    private void dismissDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 立即聊天点击事件处理
     * @param view
     */
    public void onStarChatNowClick(View view) {
        Intent intent = new Intent(getContext(), PublicActivity.class);
        intent.putExtra("user_name", userName);
        intent.putExtra("display_name", displayName);
        PublicActivity.startActivity((Activity) getContext(), "com.weapon.joker.app.message.single.SingleFragment", intent);
    }
}
