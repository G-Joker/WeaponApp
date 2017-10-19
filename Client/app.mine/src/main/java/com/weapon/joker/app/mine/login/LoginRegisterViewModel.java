package com.weapon.joker.app.mine.login;

import android.app.Activity;
import android.databinding.Bindable;
import android.view.View;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.weapon.joker.app.mine.BR;
import com.weapon.joker.app.mine.R;
import com.weapon.joker.lib.mvvm.common.PublicActivity;
import com.weapon.joker.lib.net.JMessageCallBack;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * class：   Client
 * author：  xiaweizi
 * date：    2017/10/4 20:00
 * e-mail:   1012126908@qq.com
 * desc:     登录注册 viewModel
 */
public class LoginRegisterViewModel extends LoginRegisterContact.ViewModel {

    /**
     * 用户名
     */
    @Bindable
    public String userName;
    /**
     * 用户密码
     */
    @Bindable
    public String password;

    /**
     * progressBar 是否可见
     */
    @Bindable
    public boolean pbVisible = false;

    public void setPbVisible(boolean pbVisible) {
        this.pbVisible = pbVisible;
        notifyPropertyChanged(BR.pbVisible);
    }

    /*============================== 事件点击 ===================================*/
    /**
     * 登录按钮点击处理
     * @param view
     */
    public void onLoginClick(View view) {
        if (!getView().checkInputContent()) {
            return;
        }
        setPbVisible(true);
        requestLogin(userName, password);
    }

    /**
     * 注册按钮点击处理
     * @param view
     */
    public void onRegisterClick(View view) {
        PublicActivity.startActivity((Activity) getContext(), "com.weapon.joker.app.mine.login.RegisterFragment");
    }


    /*============================== 网络请求 ===================================*/
    /**
     * 请求登录接口
     * @param userName 用户名
     * @param password 登录密码
     */
    @Override
    void requestLogin(String userName, String password) {
        // 使用 JMessage 提供的登录接口
        JMessageClient.login(userName, password, new JMessageCallBack() {
            @Override
            public void onSuccess() {
                loginSuccess();
            }

            @Override
            public void onFailed(int status, String desc) {
                loginFailed(desc);
            }
        });
    }

    /**
     * 请求注册接口
     * @param userName 用户名
     * @param password 登录密码
     */
    @Override
    void requestRegister(String userName, String password) {

    }


    /*============================== 网络请求回调处理 ===================================*/
    /**
     * 登录失败
     * @param desc 失败描述
     */
    private void loginFailed(String desc) {
        Toast.makeText(getContext(), desc, Toast.LENGTH_SHORT).show();
        setPbVisible(false);
    }

    /**
     * 登录成功
     */
    private void loginSuccess() {
        Toast.makeText(getContext(), R.string.mine_login_success, Toast.LENGTH_SHORT).show();
        // 获取本地用户信息
        UserInfo myInfo = JMessageClient.getMyInfo();
        // 统计用户信息
        MobclickAgent.onProfileSignIn(userName);
        // 统计用户点击登录事件
        MobclickAgent.onEvent(getContext().getApplicationContext(), "mine_login", userName);
        // JPush 设置 alias
        JPushInterface.setAlias(getContext().getApplicationContext(), 12, String.valueOf(myInfo.getUserID()));
        getView().finish();
    }

}
