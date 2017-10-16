package com.weapon.joker.app.mine.login;

import com.weapon.joker.lib.mvvm.common.BaseModel;
import com.weapon.joker.lib.mvvm.common.BaseView;
import com.weapon.joker.lib.mvvm.common.BaseViewModel;
import com.weapon.joker.lib.net.model.LoginModel;
import com.weapon.joker.lib.net.model.RegisterModel;

import io.reactivex.Observable;

/**
 * class：   Client
 * author：  xiaweizi
 * date：    2017/10/14 20:45
 * e-mail:   1012126908@qq.com
 * desc:     登录注册的管理类
 */
interface LoginRegisterContact {

    interface View extends BaseView{
        boolean checkInputContent();
    }

    abstract class ViewModel extends BaseViewModel<View, Model>{
        abstract void requestLogin(String userName, String password);
        abstract void requestRegister(String userName, String password);
    }

    abstract class Model extends BaseModel<ViewModel> {
        abstract Observable<LoginModel> login(String userName, String password);
        abstract Observable<RegisterModel> register(String userName, String password);
    }
}
