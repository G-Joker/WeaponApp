package com.weapon.joker.app.mine.person;

import android.content.DialogInterface;
import android.databinding.Bindable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.weapon.joker.app.mine.BR;
import com.weapon.joker.lib.middleware.utils.PreferencesUtils;

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

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }


    /**
     * 获取用户信息
     */
    public void getUserInfo(String userName) {
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
     * @param userInfo 用户信息
     */
    private void getUserInfoSuccess(UserInfo userInfo) {
        String userName = userInfo.getUserName();
        if (!TextUtils.isEmpty(userName)) {
            setUserName(userName);
        }
    }

    /**
     * 获取用户信息失败
     * @param desc 失败描述
     */
    private void getUserInfoFailed(String desc) {
        Toast.makeText(getContext(), desc, Toast.LENGTH_SHORT).show();
    }


    /**
     * 注销登录点击处理
     *
     * @param view
     */
    public void onLogoutClick(View view) {
        new AlertDialog.Builder(getContext())
                .setTitle("提示")
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
     * 退出登录相关操作
     */
    private void logout() {
        // 清楚本地的用户数据
        PreferencesUtils.clear(getContext());
        // JMessage 的登出
        JMessageClient.logout();
        // JPush ailas 置为空
        JPushInterface.setAlias(getContext(), 2, "");
        getView().finish();
    }

}
