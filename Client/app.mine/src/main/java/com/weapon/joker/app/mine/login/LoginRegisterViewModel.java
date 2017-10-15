package com.weapon.joker.app.mine.login;

import android.databinding.Bindable;

import com.weapon.joker.app.mine.BR;
import com.weapon.joker.lib.net.BaseObserver;
import com.weapon.joker.lib.net.model.LoginModel;
import com.weapon.joker.lib.net.model.RegisterModel;

/**
 * class：   Client
 * author：  xiaweizi
 * date：    2017/10/4 20:00
 * e-mail:   1012126908@qq.com
 * desc:     登录 viewModel
 */
public class LoginRegisterViewModel extends LoginRegisterContact.ViewModel {

    /**
     * 是否是登录界面
     */
    @Bindable
    public boolean isLoginView = true;

    public void setLoginView(boolean loginView) {
        isLoginView = loginView;
        notifyPropertyChanged(BR.isLoginView);
    }





    /*************************** 网络请求 ***************************/
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

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

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
        getModel().register(userName, password)
                .subscribe(new BaseObserver<RegisterModel>() {
                    @Override
                    protected void onSuccess(RegisterModel entry) throws Exception {

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }
}
