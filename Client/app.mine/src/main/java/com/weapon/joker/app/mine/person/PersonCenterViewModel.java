package com.weapon.joker.app.mine.person;

import android.content.DialogInterface;
import android.databinding.Bindable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.weapon.joker.app.mine.BR;
import com.weapon.joker.lib.middleware.utils.AlerDialogFactory;
import com.weapon.joker.lib.middleware.utils.PreferencesUtils;
import com.weapon.joker.lib.net.JMessageCallBack;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.mine.person.PersonCenterViewModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/13
 *     desc   : 个人中心 ViewModel
 * </pre>
 */

public class PersonCenterViewModel extends PersonCenterContact.ViewModel {

    @Bindable
    public String userName;
    /**
     * 个性签名
     */
    @Bindable
    public String signature;

    /**
     * 当前用户信息
     */
    private UserInfo mUserInfo;
    private AlertDialog loadingDialog;

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    public void setSignature(String signature) {
        this.signature = signature;
        notifyPropertyChanged(BR.signature);
    }

    @Override
    public void attachView(PersonCenterContact.View view) {
        super.attachView(view);
        loadingDialog = AlerDialogFactory.createLoadingDialog(getContext(), "正在更新...");
        loadingDialog.setCancelable(false);
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(String userName) {
        mUserInfo = JMessageClient.getMyInfo();
        JMessageClient.getUserInfo(userName, new GetUserInfoCallback() {
            @Override
            public void gotResult(int status, String desc, UserInfo userInfo) {
                if (status == 0) {
                    getUserInfoSuccess(userInfo);
                } else {
                    getUserInfoFailed(desc);
                }
            }
        });

    }

    /**
     * 成功获取用户信息
     *
     * @param userInfo 用户信息
     */
    private void getUserInfoSuccess(UserInfo userInfo) {
        String userName = userInfo.getNickname();
        if (!TextUtils.isEmpty(userName)) {
            setUserName(userName);
        }
        // 设置个性签名
        setSignature(TextUtils.isEmpty(userInfo.getSignature()) ? "暂无个性签名" : userInfo.getSignature());
    }

    /**
     * 获取用户信息失败
     *
     * @param desc 失败描述
     */
    private void getUserInfoFailed(String desc) {
        Toast.makeText(getContext(), desc, Toast.LENGTH_SHORT).show();
    }



    /*===============================按钮点击事件的处理========================================*/
    /**
     * 注销登录点击处理
     *
     * @param view
     */
    public void onLogoutClick(View view) {
        new AlertDialog.Builder(getContext()).setTitle("提示")
                                             .setMessage("确定退出登录？")
                                             .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                 @Override
                                                 public void onClick(DialogInterface dialog, int which) {
                                                     logout();
                                                 }
                                             })
                                             .setNegativeButton("取消", null)
                                             .show();

    }

    /**
     * 更新昵称
     *
     * @param view
     */
    public void updateUserName(View view) {
        AlerDialogFactory.createOneEditDialog(getContext(), "更新昵称", new AlerDialogFactory.OnOneEditDialogConfirmListener() {
            @Override
            public void onOneEditDialogConfirm(final String etContent) {
                if (!loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
                mUserInfo.setNickname(etContent);
                JMessageClient.updateMyInfo(UserInfo.Field.nickname, mUserInfo, new JMessageCallBack() {
                    @Override
                    public void onSuccess() {
                        setUserName(etContent);
                        dismissDialog();
                        Toast.makeText(getContext(), "更新昵称成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed(int status, String desc) {
                        Toast.makeText(getContext(), desc, Toast.LENGTH_SHORT).show();
                        dismissDialog();
                    }
                });
            }
        }).show();
    }

    /**
     * 更新个性签名
     *
     * @param view
     */
    public void updateSignature(View view) {
        AlerDialogFactory.createOneEditDialog(getContext(), "更新个性签名", new AlerDialogFactory.OnOneEditDialogConfirmListener() {
            @Override
            public void onOneEditDialogConfirm(final String etContent) {
                if (!loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
                mUserInfo.setSignature(etContent);
                JMessageClient.updateMyInfo(UserInfo.Field.signature, mUserInfo, new JMessageCallBack() {
                    @Override
                    public void onSuccess() {
                        setSignature(etContent);
                        dismissDialog();
                        Toast.makeText(getContext(), "更新个性签名成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed(int status, String desc) {
                        Toast.makeText(getContext(), desc, Toast.LENGTH_SHORT).show();
                        dismissDialog();
                    }
                });
            }
        }).show();
    }

    /**
     * 退出登录相关操作
     */
    private void logout() {
        // 清楚本地的用户数据
        PreferencesUtils.clear(getContext());
        // JMessage 的登出
        JMessageClient.logout();
        // JPush alias 置为空
        JPushInterface.setAlias(getContext(), 2, "");
        getView().finish();
    }

    private void dismissDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

}
