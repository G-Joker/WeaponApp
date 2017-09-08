package com.weapon.joker.lib.mvvm.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


/**
 * BaseActivity MVVM 基类 Activity
 * author:张冠之
 * time: 2017/9/2 下午11:05
 * e-mail: guanzhi.zhang@sojex.cn
 */

public abstract class BaseActivity<VM extends BaseViewModel,M extends BaseModel> extends AppCompatActivity implements BaseView{
    public VM mViewModel;
    public ViewDataBinding viewDataBinding;
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
        initActivity();
        initView();
    }


    /*********************基类实现***********************/
    //设置 layout 之前的配置
    private void doBeforeSetcontentView() {}

    //MVVM 绑定的初始化
    private void initActivity() {

        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());

//        ButterKnife.bind(this);

        //在某些特殊情况需要用到 Context 对象
        mContext = this;

        //反射生成泛型类对象
        mViewModel = TUtil.getT(this,0);
        M model = TUtil.getT(this,1);
        //VM 和 View 绑定
        if (mViewModel != null) {
            mViewModel.setView(this);
            mViewModel.setModel(model);
            mViewModel.setContext(this);
        }
        if (model != null){
            model.setViewModel(mViewModel);
        }

        //DataBinding 绑定
        viewDataBinding.setVariable(getBR(), mViewModel);

        mViewModel.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ButterKnife.unbind(this);
    }

    public VM getViewModel(){
        return mViewModel;
    }

    public abstract int getBR();
}
