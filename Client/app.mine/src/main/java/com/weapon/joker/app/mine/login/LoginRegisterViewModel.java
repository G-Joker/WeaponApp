package com.weapon.joker.app.mine.login;

import android.app.Activity;
import android.databinding.Bindable;
import android.view.View;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.weapon.joker.app.mine.R;
import com.weapon.joker.lib.mvvm.common.PublicActivity;
import com.weapon.joker.lib.net.BaseObserver;
import com.weapon.joker.lib.net.data.UserData;
import com.weapon.joker.lib.net.model.BaseResModel;
import com.weapon.joker.lib.net.model.LoginModel;

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



    /*============================== 事件点击 ===================================*/
    /**
     * 登录按钮点击处理
     * @param view
     */
    public void onLoginClick(View view) {
        if (!getView().checkInputContent()) {
            return;
        }
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
        getModel().login(userName, password)
                  .subscribe(new BaseObserver<LoginModel>() {
                      @Override
                      protected void onSuccess(LoginModel entry) throws Exception {
                          if (entry != null) {
                              if (entry.status == BaseResModel.REQUEST_SUCCESS && entry.data != null) {
                                  loginSuccess(entry);
                              } else {
                                  loginFailed(entry.desc);
                              }
                          } else {
                              loginFailed(getContext().getString(R.string.public_net_error));
                          }
                      }

                      @Override
                      protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                          loginFailed(getContext().getString(R.string.public_net_error));
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
    }

    /**
     * 登录成功
     * @param entry 登录成功返回的实体 bean
     */
    private void loginSuccess(LoginModel entry) {
        Toast.makeText(getContext(), R.string.mine_login_success, Toast.LENGTH_SHORT).show();
        /** 统计用户信息 */
        MobclickAgent.onProfileSignIn(entry.data.uid);
        /** 统计用户点击登录事件 */
        MobclickAgent.onEvent(getContext().getApplicationContext(), "mine_login", entry.data.user);
        /** 保存用户信息到缓存 */
        UserData.getInstance().setUserBean(getContext().getApplicationContext(), entry.data);
        getView().finish();
    }
}
