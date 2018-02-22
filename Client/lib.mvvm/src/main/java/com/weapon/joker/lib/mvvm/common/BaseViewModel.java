package com.weapon.joker.lib.mvvm.common;


import android.content.Context;
import android.databinding.BaseObservable;

import java.lang.ref.WeakReference;

/**
 * BaseViewModel VM 基类
 * 可用作页面 VM 和 adapter VM
 * 页面 VM 的时候为 BaseViewModel<V extends BaseView, M extends BaseModel>
 * adapter VM 的时候为 BaseViewModel<void,M extends BaseEntry>
 * author:张冠之
 * time: 2017/9/8 下午6:56
 * e-mail: guanzhi.zhang@sojex.cn
 */

public abstract class BaseViewModel<V , M> extends BaseObservable {
    private WeakReference<V> mView;
    private M mModel;
    private Context mContext;

    public BaseViewModel() {

    }

    public V getView() {
        return mView == null ? null : mView.get();
    }


    public void attachView(V view){
        mView = new WeakReference<V>(view);
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


    public void detachView() {
        if (mView != null) {
            mView.clear();
            mView = null;
        }
    }

}
