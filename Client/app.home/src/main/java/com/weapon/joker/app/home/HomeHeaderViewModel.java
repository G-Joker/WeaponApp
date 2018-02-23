package com.weapon.joker.app.home;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.weapon.joker.lib.net.bean.HomeBean.RecommandHeadValue;

/**
 * HomeHeaderViewModel 列表Header的ViewModel
 * author:张冠之
 * time: 2018/2/23 上午10:58
 * e-mail: guanzhi.zhang@sojex.cn
 */
public class HomeHeaderViewModel extends BaseObservable{

    @Bindable
    public RecommandHeadValue bean;

    public HomeHeaderViewModel(RecommandHeadValue bean){
        this.bean = bean;
    }


}
