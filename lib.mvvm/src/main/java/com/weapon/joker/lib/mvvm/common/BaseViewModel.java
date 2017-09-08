package com.weapon.joker.lib.mvvm.common;


import android.content.Context;
import android.databinding.BaseObservable;

/**
 * BaseViewModel VM 基类
 * author:张冠之
 * time: 2017/9/8 下午6:56
 * e-mail: guanzhi.zhang@sojex.cn
 */

public abstract class BaseViewModel<V extends BaseView, M extends BaseModel> extends BaseObservable {
    private V mView;
    private M mModel;
    private Context mContext;

    public BaseViewModel() {

    }

    public V getView() {
        return mView;
    }


    public void setView(V v) {
        mView = v;
    }

    public M getModel() {
        return mModel;
    }

    public void setModel(M m) {
        mModel = m;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    protected abstract void init();

}
