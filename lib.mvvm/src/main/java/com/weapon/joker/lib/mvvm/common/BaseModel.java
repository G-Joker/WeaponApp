package com.weapon.joker.lib.mvvm.common;

/**
 * Created by pc on 2017/9/2.
 */

public abstract class BaseModel<VM extends BaseViewModel>{
    private VM mViewModel;

    public VM getViewModel(){
        return mViewModel;
    }

    public void setViewModel(VM viewModel){
        mViewModel = viewModel;
    }
}
