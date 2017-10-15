package com.weapon.joker.app.mine.login;

import com.weapon.joker.lib.net.Api;
import com.weapon.joker.lib.net.HostType;
import com.weapon.joker.lib.net.model.LoginModel;
import com.weapon.joker.lib.net.model.RegisterModel;
import com.weapon.joker.lib.net.rx.RxSchedulers;

import io.reactivex.Observable;

/**
 * class：   com.weapon.joker.app.mine.login.LoginRegisterModel
 * author：  xiaweizi
 * date：    2017/10/14 20:53
 * e-mail:   1012126908@qq.com
 * desc:     登录注册 model
 */
public class LoginRegisterModel extends LoginRegisterContact.Model {


    @Override
    Observable<LoginModel> login(String userName, String password) {
        return Api.getDefault(HostType.MINE)
                .login(userName, password)
                .compose(RxSchedulers.<LoginModel>io_main());
    }

    @Override
    Observable<RegisterModel> register(String userName, String password) {
        return Api.getDefault(HostType.MINE)
                .register(userName, password)
                .compose(RxSchedulers.<RegisterModel>io_main());
    }
}
