package com.weapon.joker.app.home;

import com.weapon.joker.app.home.api.HomeApi;
import com.weapon.joker.lib.net.Api;
import com.weapon.joker.lib.net.HostType;
import com.weapon.joker.lib.net.rx.RxSchedulers;

import io.reactivex.Observable;

/**
 * Created by WeaponZhi on 2018/2/20.
 */

public class HomeModel extends HomeContact.Model{

    @Override
    Observable<HomeBean> getHomeListData() {
        return Api.getDefault(HostType.HOME, HomeApi.class)
                .getHomeListData()
                .compose(RxSchedulers.<HomeBean>io_main());
    }
}
