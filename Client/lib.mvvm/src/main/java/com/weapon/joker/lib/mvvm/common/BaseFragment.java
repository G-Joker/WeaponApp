package com.weapon.joker.lib.mvvm.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.weapon.joker.lib.mvvm.util.TUtil;

import java.lang.reflect.Method;

/**
 * BaseFragment MVVM 基类 Fragment
 * author:张冠之
 * time: 2017/9/8 下午9:44
 * e-mail: guanzhi.zhang@sojex.cn
 */

public abstract class BaseFragment<VM extends BaseViewModel<? extends BaseView, ? extends BaseModel>,
        M extends BaseModel<? extends BaseViewModel>>
        extends Fragment
        implements BaseView {

    protected VM mViewModel;
    protected ViewDataBinding mViewDataBinding;
    protected Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return initFragment(inflater, container);
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
                try {
                    Method setModel = mViewModel.getClass().getMethod("setModel",Object.class);
                    Method attachView = mViewModel.getClass().getMethod("attachView", Object.class);
                    setModel.invoke(mViewModel, model);
                    attachView.invoke(mViewModel, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //Model 和 VM 绑定
            if (model != null) {
                model.setContext(mContext);
                try {
                    Method attachViewModel = mViewModel.getClass().getMethod("attachViewModel",Object.class);
                    attachViewModel.invoke(model,mViewModel);
                }catch (Exception e){
                    e.printStackTrace();
                }
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

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onEvent(getActivity(), getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.detachView();
    }
}
