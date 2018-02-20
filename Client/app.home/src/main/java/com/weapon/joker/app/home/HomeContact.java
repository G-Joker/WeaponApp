package com.weapon.joker.app.home;

import com.weapon.joker.lib.mvvm.common.BaseModel;
import com.weapon.joker.lib.mvvm.common.BaseView;
import com.weapon.joker.lib.mvvm.common.BaseViewModel;

/**
 * Created by WeaponZhi on 2018/2/20.
 */

interface HomeContact {
    interface View extends BaseView {
    }

    abstract class ViewModel extends BaseViewModel<View, Model> {

    }

    abstract class Model extends BaseModel<ViewModel> {

    }
}
