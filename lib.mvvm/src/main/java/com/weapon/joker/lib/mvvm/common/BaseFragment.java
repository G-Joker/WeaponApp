package com.weapon.joker.lib.mvvm.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * BaseFragment MVVM 基类 Fragment
 * author:张冠之
 * time: 2017/9/8 下午9:44
 * e-mail: guanzhi.zhang@sojex.cn
 */

public abstract class BaseFragment<VM extends BaseViewModel, M extends BaseModel> extends Fragment
        implements BaseView {

    protected VM mViewModel;
    protected ViewDataBinding mViewDataBinding;
    protected Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return initFragment(inflater, container);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.detachView();
    }

    private View initFragment(LayoutInflater inflater, ViewGroup container) {
        if (mViewDataBinding == null) {
            mContext = getActivity();
            mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);

            //反射生成泛型类对象
            mViewModel = TUtil.getT(this, 0);
            M model = TUtil.getT(this, 1);

            //VM 和 View 绑定
            if (mViewModel != null) {
                mViewModel.setContext(mContext);
                mViewModel.setModel(model);
                mViewModel.attachView(this);
            }

            //Model 和 VM 绑定
            if (model != null) {
                model.attachViewModel(mViewModel);
            }

            //DataBinding 绑定
            mViewDataBinding.setVariable(getBR(), mViewModel);

            initView();
        } else {
            if (mViewDataBinding.getRoot().getParent() != null) {
                ((ViewGroup) mViewDataBinding.getRoot().getParent()).removeView(mViewDataBinding.getRoot());
            }
        }
        return mViewDataBinding.getRoot();
    }

    public VM getViewModel() {
        return mViewModel;
    }

    public ViewDataBinding getViewDataBinding() {
        return mViewDataBinding;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void finish() {
        getActivity().finish();
    }
}
