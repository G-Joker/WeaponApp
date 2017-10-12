package com.weapon.joker.app.mine.login;

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
import android.widget.TextView;
import android.widget.Toast;

import com.weapon.joker.app.mine.R;
import com.weapon.joker.lib.middleware.utils.LogUtils;
import com.weapon.joker.lib.mvvm.common.BaseActivity;
import com.weapon.joker.lib.net.Api;
import com.weapon.joker.lib.net.BaseObserver;
import com.weapon.joker.lib.net.HostType;
import com.weapon.joker.lib.net.model.LoginModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
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
    private TextInputLayout      mTilUserName;
    private TextInputLayout      mTilPassword;
    private Button               mBtLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mButton = findViewById(R.id.fab);
        mTvForgetPassword = findViewById(R.id.tv_forget_password);
        mTvForgetPassword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTilUserName = findViewById(R.id.til_login_username);
        mTilPassword = findViewById(R.id.til_login_password);
        mBtLogin = findViewById(R.id.bt_login);

        mButton.setOnClickListener(this);
        mBtLogin.setOnClickListener(this);

    }

    @Override
    protected Transition getTransition() {
        return new Explode();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, mButton, mButton.getTransitionName());
                startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
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
        String userName     = mTilUserName.getEditText().getText().toString().trim();
        String password = mTilPassword.getEditText().getText().toString().trim();
        mTilPassword.setErrorEnabled(false);
        mTilUserName.setErrorEnabled(false);
        if (TextUtils.isEmpty(userName)) {
            mTilUserName.setError("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mTilPassword.setError("密码不能为空");
            return;
        }


        Api.getDefault(HostType.MINE)
           .login(userName, password)
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(new BaseObserver<LoginModel>() {
               @Override
               protected void onSuccess(LoginModel entry) throws Exception {
                   if (entry.status == 1000 && entry != null && entry.data != null) {
                       Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                       LogUtils.logi("userBean:\t" + entry.data.toString());
                   } else {
                       Toast.makeText(LoginActivity.this, "登陆失败" + entry.desc, Toast.LENGTH_SHORT).show();
                       LogUtils.logi("login:\t" + entry.desc);
                   }
               }

               @Override
               protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                   Toast.makeText(LoginActivity.this, "网络异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
                   LogUtils.logi("login:\t" + e.getMessage());
               }
           });
    }

}
