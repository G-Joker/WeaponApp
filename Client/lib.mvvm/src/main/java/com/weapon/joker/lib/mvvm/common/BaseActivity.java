package com.weapon.joker.lib.mvvm.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Transition;
import android.view.Window;

import com.umeng.analytics.MobclickAgent;
import com.weapon.joker.lib.mvvm.util.TUtil;

import java.lang.reflect.Method;


/**
 * BaseActivity MVVM 基类 Activity
 * author:张冠之
 * time: 2017/9/2 下午11:05
 * e-mail: guanzhi.zhang@sojex.cn
 */

public abstract class BaseActivity
        <VM extends BaseViewModel<? extends BaseView, ? extends BaseModel>,
        M extends BaseModel<? extends BaseViewModel>>
        extends AppCompatActivity
        implements BaseView {

    protected VM mViewModel;
//    protected ViewDataBinding mViewDataBinding;
    protected Context mContext;

    private FragmentTransaction mFragmentTransaction;
    public String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (mViewDataBinding == null){
//            mViewDataBinding = DataBindingUtil.setContentView(this,getLayoutId());
            mContext = this;
//        }

        // 添加转场动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(getTransition());
        setContentView(getLayoutId());

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
//        mViewDataBinding.setVariable(getBR(), mViewModel);

        initView();

        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        TAG = getComponentName().getShortClassName();
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public VM getViewModel() {
        return mViewModel;
    }

//    public ViewDataBinding getViewDataBinding() {
//        return mViewDataBinding;
//    }

    public void setContext(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public void startFragment(Fragment fragment, @IdRes int layout) {
        mFragmentTransaction.replace(layout, fragment).commit();
    }

    /**
     * @return 转场动画
     */
    protected  Transition getTransition(){
        return new Fade();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mViewModel != null) {
            mViewModel.detachView();
        }
    }
}
