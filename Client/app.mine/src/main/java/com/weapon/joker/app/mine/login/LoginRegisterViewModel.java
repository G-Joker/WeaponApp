package com.weapon.joker.app.mine.login;

import android.databinding.Bindable;

import com.weapon.joker.app.mine.BR;

/**
 * class：   Client
 * author：  xiaweizi
 * date：    2017/10/4 20:00
 * e-mail:   1012126908@qq.com
 * desc:     登录 viewModel
 */
public class LoginRegisterViewModel extends LoginRegisterContact.ViewModel{

    /** 是否是登录界面 */
    private boolean isLoginView = true;

    public void setLoginView(boolean loginView) {
        isLoginView = loginView;
        notifyPropertyChanged(BR.loginView);
    }

    @Bindable
    public boolean isLoginView() {
        return isLoginView;
    }
}
