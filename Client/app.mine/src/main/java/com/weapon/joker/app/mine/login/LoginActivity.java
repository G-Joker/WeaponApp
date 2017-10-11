package com.weapon.joker.app.mine.login;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.transition.Explode;
import android.transition.Transition;
import android.view.View;
import android.widget.TextView;

import com.weapon.joker.app.mine.R;
import com.weapon.joker.lib.mvvm.common.BaseActivity;

/**
 * class：   Client
 * author：  xiaweizi
 * date：    2017/10/4 18:00
 * e-mail:   1012126908@qq.com
 * desc:
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private FloatingActionButton mButton;
    private TextView mTvForgetPassword;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mButton = findViewById(R.id.fab);
        mTvForgetPassword = findViewById(R.id.tv_forget_password);
        mTvForgetPassword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mButton.setOnClickListener(this);
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
        }
    }
}
