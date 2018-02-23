package com.weapon.joker.app.home.header;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.weapon.joker.app.home.BR;
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

    public HomeHeaderViewModel(){
    }

    /**
     * 必须通过这种手动调用方式才能刷新UI
     * 在多布局中，引用已经确定的情况下， DataBinding无法通过再次构造新对象监听到 引用变动
     */
    public void setHeadValue(RecommandHeadValue bean){
        this.bean = bean;
        notifyPropertyChanged(BR.bean);
    }


}
