package com.weapon.joker.app.mine;

import android.databinding.Bindable;

/**
 * class：   WeaponApp
 * author：  xiaweizi
 * date：    2017/9/17 11:01
 * e-mail:   1012126908@qq.com
 * desc:
 */
public class MineViewModel extends MineContact.ViewModel{

    public void init(){
        setTestString("反射封装测试成功");
        getView().TestType();
    }

    @Bindable
    public String getTestString(){
        return getModel().testString;
    }

    public void setTestString(String testString){
        getModel().testString = testString;
        notifyPropertyChanged(BR.testString);
    }
}
