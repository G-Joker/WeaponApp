package com.weapon.joker.app.mine;

/**
 * class：   WeaponApp
 * author：  xiaweizi
 * date：    2017/9/17 11:01
 * e-mail:   1012126908@qq.com
 * desc:
 */
public class MineViewModel extends MineContact.ViewModel{
    public MineEntry mEntry;

    public void init(){
        mEntry = new MineEntry();
        mEntry.testString = "Test MVVP";
        notifyPropertyChanged(BR.testString);
    }
}
