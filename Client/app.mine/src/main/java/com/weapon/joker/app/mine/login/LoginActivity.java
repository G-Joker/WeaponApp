package com.weapon.joker.app.mine.login;

import android.transition.Explode;
import android.transition.Transition;

import com.weapon.joker.app.mine.R;
import com.weapon.joker.lib.mvvm.common.BaseActivity;

/**
 * class：   Client
 * author：  xiaweizi
 * date：    2017/10/4 18:00
 * e-mail:   1012126908@qq.com
 * desc:
 */
public class LoginActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

    }

    @Override
    protected Transition getTransition() {
        return new Explode();
    }
}
