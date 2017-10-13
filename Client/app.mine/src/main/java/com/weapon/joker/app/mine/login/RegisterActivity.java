package com.weapon.joker.app.mine.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.weapon.joker.app.mine.R;
import com.weapon.joker.lib.mvvm.common.BaseActivity;
import com.weapon.joker.lib.net.Api;
import com.weapon.joker.lib.net.BaseObserver;
import com.weapon.joker.lib.net.HostType;
import com.weapon.joker.lib.net.model.RegisterModel;
import com.weapon.joker.lib.net.rx.RxSchedulers;

import io.reactivex.schedulers.Schedulers;

/**
 * class：   Client
 * author：  xiaweizi
 * date：    2017/10/4 20:40
 * e-mail:   1012126908@qq.com
 * desc:
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private CardView             mCardView;
    private FloatingActionButton mFloatingActionButton;
    /** 用户名输入框 */
    private TextInputLayout      mTilUserName;
    /** 密码输入框 */
    private TextInputLayout      mTilPassword;
    /** 重新输入密码输入框 */
    private TextInputLayout      mTilPasswordAgain;
    private ProgressBar          mPbLoading;
    private Button               mBtRegister;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        mCardView = findViewById(R.id.cv_add);
        mFloatingActionButton = findViewById(R.id.fab);
        mTilUserName = findViewById(R.id.til_register_username);
        mTilPassword = findViewById(R.id.til_register_password);
        mTilPasswordAgain = findViewById(R.id.til_register_repeat_password);
        mPbLoading = findViewById(R.id.pb_register_loading);
        mBtRegister = findViewById(R.id.bt_register);

        ShowEnterAnimation();
        mFloatingActionButton.setOnClickListener(this);
        mBtRegister.setOnClickListener(this);
    }

    /**
     * 给 FloatActionButton 设置共享元素动画
     */
    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                mCardView.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    /**
     * 进入注册界面 CardView 铺满界面的动画效果
     */
    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCardView,
                                                                     mCardView.getWidth() / 2,
                                                                     0,
                                                                     mFloatingActionButton.getWidth() / 2,
                                                                     mCardView.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mCardView.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    /**
     * 退出注册界面 CardView 的动画效果i
     */
    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCardView,
                                                                     mCardView.getWidth() / 2,
                                                                     0,
                                                                     mCardView.getHeight(),
                                                                     mFloatingActionButton.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCardView.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                mFloatingActionButton.setImageResource(R.mipmap.ic_mine_register);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                animateRevealClose();
                break;
            case R.id.bt_register:
                register();
                break;
            default:
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {
        String userName      = mTilUserName.getEditText().getText().toString().trim();
        String password      = mTilPassword.getEditText().getText().toString().trim();
        String passwordAgain = mTilPasswordAgain.getEditText().getText().toString().trim();
        /** 重置输入框的状态 */
        mTilUserName.setErrorEnabled(false);
        mTilPassword.setErrorEnabled(false);
        mTilPasswordAgain.setErrorEnabled(false);
        /** 对输入框的输入内容进行判空处理 */
        if (TextUtils.isEmpty(userName)) {
            mTilUserName.setError(getString(R.string.mine_username_null));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mTilPassword.setError(getString(R.string.mine_password_null));
            return;
        }
        if (TextUtils.isEmpty(passwordAgain)) {
            mTilPasswordAgain.setError(getString(R.string.mine_password_null));
            return;
        }
        if (!TextUtils.equals(password, passwordAgain)) {
            mTilPasswordAgain.setError(getString(R.string.mine_password_different));
            return;
        }
        mBtRegister.setVisibility(View.GONE);
        mPbLoading.setVisibility(View.VISIBLE);
        registerRequest(userName, password);
    }

    /**
     * 注册请求
     * @param userName 用户名
     * @param password 密码
     */
    private void registerRequest(String userName, String password) {
        Api.getDefault(HostType.MINE)
           .register(userName, password)
           .subscribeOn(Schedulers.io())
           .compose(RxSchedulers.<RegisterModel>io_main())
           .subscribe(new BaseObserver<RegisterModel>() {
               @Override
               protected void onSuccess(RegisterModel entry) throws Exception {
                   if (entry != null && entry.status == 1000 && entry.data != null) {
                       registerSuccess(entry);
                   } else if (entry != null) {
                       registerFailed(entry.desc);
                   } else {
                       registerFailed(getString(R.string.public_net_error));
                   }
               }

               @Override
               protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                   registerFailed(e.getMessage());
               }
           });
    }

    /**
     * 登陆成功
     *
     * @param model
     */
    private void registerSuccess(RegisterModel model) {
        Toast.makeText(this, getString(R.string.mine_register_repeat), Toast.LENGTH_SHORT).show();
        MobclickAgent.onEvent(getApplicationContext(), "mine_register", model.data.user);

        /** 将结果回传给登陆界面 */
        Intent result = new Intent();
        result.putExtra("user_name", model.data.user);
        result.putExtra("password", mTilPassword.getEditText().getText().toString().trim());
        setResult(201, result);

        mPbLoading.setVisibility(View.GONE);
        mBtRegister.setVisibility(View.VISIBLE);
        mBtRegister.setOnClickListener(null);
        mBtRegister.setText(getString(R.string.mine_register_repeat));
        mBtRegister.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 500);
    }

    /**
     * 登陆失败
     *
     * @param desc 失败描述
     */
    private void registerFailed(String desc) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
        mPbLoading.setVisibility(View.GONE);
        mBtRegister.setVisibility(View.VISIBLE);
        mBtRegister.setText(getString(R.string.mine_login_repeat));
    }
}
