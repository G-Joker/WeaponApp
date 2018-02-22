package com.weapon.joker.app.home;

import android.view.View;

import com.weapon.joker.lib.middleware.utils.LogUtils;
import com.weapon.joker.lib.net.BaseObserver;
import com.weapon.joker.lib.net.bean.HomeBean.HomeBean;

/**
 * HomeViewModel 首页Fragment ViewModel
 * author:张冠之
 * time: 2018/2/22 上午10:47
 * e-mail: guanzhi.zhang@sojex.cn
 */
public class HomeViewModel extends HomeContact.ViewModel {


    @Override
    void requestRecommandData() {
        getModel().getHomeListData().subscribe(new BaseObserver<HomeBean>() {
            @Override
            protected void onSuccess(HomeBean entry) throws Exception {
                LogUtils.i("onSuccess",entry.ecode);
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                LogUtils.i("onFailure",e.getMessage());
            }
        });
    }

    /** 二维码点击事 */
    public void onQrcodeClick(View view) {
        LogUtils.i("qrcode is clicked");
    }

    /** 菜单点击事件 */
    public void onCategoryClick(View view) {
        LogUtils.i("category is clicked");
    }

    /** 索引点击事件 */
    public void onSearchTextClick(View view) {
        LogUtils.i("search is clicked");
    }
}
