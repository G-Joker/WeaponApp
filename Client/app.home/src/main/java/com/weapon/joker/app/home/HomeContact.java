package com.weapon.joker.app.home;

import com.weapon.joker.lib.mvvm.common.BaseModel;
import com.weapon.joker.lib.mvvm.common.BaseView;
import com.weapon.joker.lib.mvvm.common.BaseViewModel;
import com.weapon.joker.lib.net.bean.HomeBean.HomeBean;

import io.reactivex.Observable;

/**
 * Created by WeaponZhi on 2018/2/20.
 */

interface HomeContact {
    interface View extends BaseView {
    }

    abstract class ViewModel extends BaseViewModel<View, Model> {
        abstract void requestRecommandData();//请求首页列表数据
    }

    abstract class Model extends BaseModel<ViewModel> {
        abstract Observable<HomeBean> getHomeListData();
    }
}
