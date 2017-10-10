package com.weapon.joker.app.mine.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.weapon.joker.app.mine.R;
import com.weapon.joker.app.mine.databinding.ActivityLoginBinding;

/**
 * class：   Client
 * author：  xiaweizi
 * date：    2017/10/4 18:00
 * e-mail:   1012126908@qq.com
 * desc:
 */
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding mBinding;
    private LoginViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mViewModel = new LoginViewModel();
        mBinding.setModel(mViewModel);
        mBinding.setPresenter(new Presenter());
    }

    public class Presenter {

        public void registerOnClick(View view) {
            getWindow().setEnterTransition(null);
            getWindow().setExitTransition(null);

            ActivityOptionsCompat optionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this, mBinding.fab, mBinding.fab.getTransitionName());


        }
    }
}
