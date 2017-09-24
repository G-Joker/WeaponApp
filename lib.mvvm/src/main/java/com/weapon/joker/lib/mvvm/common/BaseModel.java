package com.weapon.joker.lib.mvvm.common;

import android.databinding.BaseObservable;

/**
 * Created by pc on 2017/9/2.
 */

public abstract class BaseModel<VM extends BaseViewModel> extends BaseObservable{
    private VM mViewModel;

    public VM getViewModel(){
        return mViewModel;
    }

    public void attachViewModel(VM viewModel){mViewModel = viewModel;}
}
