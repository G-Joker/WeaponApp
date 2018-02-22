package com.weapon.joker.app.home;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.weapon.joker.lib.net.bean.HomeBean.RecommandBodyValue;

/**
 * HomeItemViewModel 首页ItemViewModel
 * author:张冠之
 * time: 2018/2/22 下午2:10
 * e-mail: guanzhi.zhang@sojex.cn
 */
public class HomeItemViewModel extends BaseObservable {
    public int type = CARD_TYPE_ONE;
    public static final int VIDEO_TYPE = 0x00;
    public static final int CARD_TYPE_ONE = 0x01;
    public static final int CARD_TYPE_TWO = 0x02;
    public static final int CARD_TYPE_THREE = 0x03;

    @Bindable
    public RecommandBodyValue bean;


    public HomeItemViewModel(RecommandBodyValue bean){
        this.type = bean.type;
        this.bean = bean;
    }

}
