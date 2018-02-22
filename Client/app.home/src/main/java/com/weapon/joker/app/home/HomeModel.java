package com.weapon.joker.app.home;

import com.weapon.joker.lib.net.Api;
import com.weapon.joker.lib.net.HostType;
import com.weapon.joker.lib.net.bean.HomeBean.HomeBean;
import com.weapon.joker.lib.net.rx.RxSchedulers;

import io.reactivex.Observable;

/**
 * HomeModel 首页数据请求model
 * author:张冠之
 * time: 2018/2/22 上午11:04
 * e-mail: guanzhi.zhang@sojex.cn
 */
public class HomeModel extends HomeContact.Model{

    @Override
    Observable<HomeBean> getHomeListData() {
        return Api.getDefault(HostType.HOME)
                .getHomeListData()
                .compose(RxSchedulers.<HomeBean>io_main());
    }
}
