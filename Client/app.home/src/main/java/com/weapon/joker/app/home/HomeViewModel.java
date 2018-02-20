package com.weapon.joker.app.home;

import com.weapon.joker.lib.middleware.utils.LogUtils;
import com.weapon.joker.lib.net.BaseObserver;

/**
 * Created by WeaponZhi on 2018/2/20.
 */

public class HomeViewModel extends HomeContact.ViewModel{


    @Override
    void requestRecommandData() {
        getModel().getHomeListData().subscribe(new BaseObserver<HomeBean>() {
            @Override
            protected void onSuccess(HomeBean entry) throws Exception {
                LogUtils.i("onSuccess",entry.toString());
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                LogUtils.i("onFailure",e.getMessage());
            }
        });
    }
}
