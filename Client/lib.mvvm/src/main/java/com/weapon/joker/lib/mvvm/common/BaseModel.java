package com.weapon.joker.lib.mvvm.common;

import android.content.Context;
import android.databinding.BaseObservable;

/**
 * Created by pc on 2017/9/2.
 */

public abstract class BaseModel<VM extends BaseViewModel> extends BaseObservable{
    private VM mViewModel;
    private Context mContext;

    public VM getViewModel(){
        return mViewModel;
    }

    public void attachViewModel(VM viewModel){
        mViewModel = viewModel;
    }

    public void setContext(Context context){
        mContext = context.getApplicationContext();
    }

    public Context getContext(){
        return mContext;
    }
}
