package com.weapon.joker.app.message.friend;

import android.app.Activity;
import android.content.Intent;
import android.databinding.Bindable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;
import com.weapon.joker.lib.middleware.PublicActivity;
import com.weapon.joker.lib.middleware.utils.AlertDialogFactory;

import java.io.File;

import cn.jpush.im.android.api.model.UserInfo;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.friend.SearchFriendViewModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/31
 *     desc   :
 * </pre>
 */

public class SearchFriendViewModel extends SearchFriendContact.ViewModel {

    /**
     * 搜索的用户名
     */
    @Bindable
    public String userName;
    /**
     * 搜索的展示名
     */
    @Bindable
    public String displayName;
    @Bindable
    public File avatarFile;
    @Bindable
    public boolean searchItemVisible = false;
    @Bindable
    public boolean noUserVisible = false;
    private AlertDialog mLoadingDialog;

    public void setSearchItemVisible(boolean searchItemVisible) {
        this.searchItemVisible = searchItemVisible;
        notifyPropertyChanged(BR.searchItemVisible);
    }

    public void setNoUserVisible(boolean noUserVisible) {
        this.noUserVisible = noUserVisible;
        notifyPropertyChanged(BR.noUserVisible);
    }

    public void setAvatarFile(File avatarFile) {
        this.avatarFile = avatarFile;
        notifyPropertyChanged(BR.avatarFile);
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        notifyPropertyChanged(BR.displayName);
    }

    @Override
    public void attachView(SearchFriendContact.View view) {
        super.attachView(view);
        mLoadingDialog = AlertDialogFactory.createLoadingDialog(getContext(), "正在搜索中...");
    }

    @Override
    void searchSuccess(UserInfo userInfo) {
        dismissDialog();
        setSearchItemVisible(true);
        setNoUserVisible(false);
        setAvatarFile(userInfo.getAvatarFile());
        setDisplayName(userInfo.getDisplayName());
    }

    @Override
    void searchFailed(String desc) {
        dismissDialog();
        setSearchItemVisible(false);
        setNoUserVisible(true);
        Toast.makeText(getContext(), "搜索失败:\t" + desc, Toast.LENGTH_SHORT).show();
    }

    public void onFriendClick(View view) {
        Intent intent = new Intent(getContext(), PublicActivity.class);
        intent.putExtra("user_name", userName);
        intent.putExtra("display_name", displayName);
        PublicActivity.startActivity((Activity) getContext(), "com.weapon.joker.app.message.single.SingleFragment", intent);
    }

    public void onAvatarClick(View view) {
        Intent intent = new Intent(view.getContext(), PublicActivity.class);
        intent.putExtra("user_name", userName);
        PublicActivity.startActivity((Activity) view.getContext(), "com.weapon.joker.app.message.homepage.HomePageFragment", intent);
    }

    public void onSearchFriendClick(View view) {
        if (checkInputContent()) {
            mLoadingDialog.show();
            getModel().doSearchFriend(userName);
        }
    }

    private boolean checkInputContent() {
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getContext(), getContext().getString(R.string.mine_username_null), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void dismissDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }
}
