package com.weapon.joker.app.mine.login;

import com.weapon.joker.lib.mvvm.common.BaseModel;
import com.weapon.joker.lib.mvvm.common.BaseView;
import com.weapon.joker.lib.mvvm.common.BaseViewModel;

/**
 * class：   Client
 * author：  xiaweizi
 * date：    2017/10/14 20:45
 * e-mail:   1012126908@qq.com
 * desc:     登录注册的管理类
 */
interface LoginRegisterContact {

    interface View extends BaseView{

    }

    abstract class ViewModel extends BaseViewModel<View, Model>{

    }

    abstract class Model extends BaseModel<ViewModel> {

    }
}
