package com.weapon.joker.lib.mvvm.common;

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


/**
 * BaseActivity MVVM 基类 Activity
 * author:张冠之
 * time: 2017/9/2 下午11:05
 * e-mail: guanzhi.zhang@sojex.cn
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    private FragmentTransaction mFragmentTransaction;
    public String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加转场动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(getTransition());
        setContentView(getLayoutId());
        initView();
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        TAG = getComponentName().getShortClassName();
    }

    @Override
    public int getBR() {
        return 0;
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void startFragment(Fragment fragment, @IdRes int layout) {
        mFragmentTransaction.replace(layout, fragment).commit();
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

    /**
     * @return 转场动画
     */
    protected  Transition getTransition(){
        return new Fade();
    }
}
