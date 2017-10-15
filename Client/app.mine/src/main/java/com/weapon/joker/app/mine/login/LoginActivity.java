package com.weapon.joker.app.mine.login;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.transition.Explode;
import android.transition.Transition;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.weapon.joker.app.mine.R;
import com.weapon.joker.lib.mvvm.common.BaseActivity;
import com.weapon.joker.lib.net.Api;
import com.weapon.joker.lib.net.ApiConvertUtil;
import com.weapon.joker.lib.net.BaseObserver;
import com.weapon.joker.lib.net.HostType;
import com.weapon.joker.lib.net.bean.UserBean;
import com.weapon.joker.lib.net.data.UserData;
import com.weapon.joker.lib.net.model.LoginModel;
import com.weapon.joker.lib.net.model.LoginRequestModel;
import com.weapon.joker.lib.net.rx.RxSchedulers;

import io.reactivex.schedulers.Schedulers;

/**
 * class：   Client
 * author：  xiaweizi
 * date：    2017/10/4 18:00
 * e-mail:   1012126908@qq.com
 * desc:
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private FloatingActionButton mButton;
    private TextView             mTvForgetPassword;
    /** 登录用户名 */
    private TextInputLayout      mTilUserName;
    /** 登录密码 */
    private TextInputLayout      mTilPassword;
    /** 登录按钮 */
    private Button               mBtLogin;
    private ProgressBar          mPbLoading;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mButton = findViewById(R.id.fab);
        mTvForgetPassword = findViewById(R.id.tv_forget_password);
        /** 忘记密码设置下划线 */
        mTvForgetPassword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTilUserName = findViewById(R.id.til_login_username);
        mTilPassword = findViewById(R.id.til_login_password);
        mBtLogin = findViewById(R.id.bt_login);
        mPbLoading = findViewById(R.id.pb_loading);

        mButton.setOnClickListener(this);
        mBtLogin.setOnClickListener(this);

    }

    @Override
    protected Transition getTransition() {
        return new Explode();
    }

    @SuppressLint ("RestrictedApi")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                /** 添加跳转到注册界面的转场动画 */
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, mButton, mButton.getTransitionName());
                startActivityForResult(new Intent(this, RegisterActivity.class), 200, options.toBundle());
                break;
            case R.id.bt_login:
                // 登陆
                login();
                break;
            default:
                break;
        }
    }

    /**
     * 登陆
     */
    private void login() {
        String userName = mTilUserName.getEditText().getText().toString().trim();
        String password = mTilPassword.getEditText().getText().toString().trim();
        /** 重置输入框的状态 */
        mTilPassword.setErrorEnabled(false);
        mTilUserName.setErrorEnabled(false);
        /** 判断用户名和密码是否为空 */
        if (TextUtils.isEmpty(userName)) {
            mTilUserName.setError(getString(R.string.mine_username_null));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mTilPassword.setError(getString(R.string.mine_password_null));
            return;
        }

        mPbLoading.setVisibility(View.VISIBLE);
        mBtLogin.setVisibility(View.GONE);
        loginRequest(userName, password);
    }

    /**
     * 登录请求
     * @param userName 用户名
     * @param password 密码
     */
    private void loginRequest(String userName, String password) {
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.name = userName;
        loginRequestModel.password = password;
        Api.getDefault(HostType.MINE)
                .login(ApiConvertUtil.beanToMap(loginRequestModel))
           .subscribeOn(Schedulers.io())
           .compose(RxSchedulers.<LoginModel>io_main())
           .subscribe(new BaseObserver<LoginModel>() {
               @Override
               protected void onSuccess(LoginModel entry) throws Exception {
                   if (entry != null && entry.status == 1000 && entry.data != null) {
                       loginSuccess(entry.data);
                   } else if (entry != null) {
                       loginFailed(entry.desc);
                   } else {
                       loginFailed(getString(R.string.public_net_error));
                   }
               }

               @Override
               protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                   loginFailed(e.getMessage());
               }
           });
    }

    /**
     * 登陆失败
     *
     * @param desc 失败相关描述
     */
    private void loginFailed(String desc) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
        mPbLoading.setVisibility(View.GONE);
        mBtLogin.setVisibility(View.VISIBLE);
        mBtLogin.setText(getString(R.string.mine_login_repeat));
    }

    /**
     * 登陆成功
     *
     * @param data 用户信息
     */
    private void loginSuccess(UserBean data) {
        Toast.makeText(this, getString(R.string.mine_login_success), Toast.LENGTH_SHORT).show();
        /** 统计用户信息 */
        MobclickAgent.onProfileSignIn(data.uid);
        /** 统计用户点击登录事件 */
        MobclickAgent.onEvent(getApplicationContext(), "mine_login", data.user);
        /** 保存用户信息到缓存 */
        UserData.getInstance().setUserBean(this,data);

        mPbLoading.setVisibility(View.GONE);
        mBtLogin.setVisibility(View.VISIBLE);
        mBtLogin.setOnClickListener(null);
        mBtLogin.setText(getString(R.string.mine_login_success));
        mBtLogin.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 500);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200 && resultCode == 201 && data != null) {
            /** 拿到注册成功后的用户名和密码 */
            String userName = data.getStringExtra("user_name");
            String password = data.getStringExtra("password");
            mTilUserName.getEditText().setText(userName);
            mTilPassword.getEditText().setText(password);
        }
    }
}
