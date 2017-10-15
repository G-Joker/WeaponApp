package com.weapon.joker.app.mine.login;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.weapon.joker.app.mine.BR;
import com.weapon.joker.app.mine.R;
import com.weapon.joker.app.mine.databinding.FragmentLoginBinding;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

/**
 * class：   com.weapon.joker.app.mine.login.LoginRegisterFragment
 * author：  xiaweizi
 * date：    2017/10/14 20:54
 * e-mail:   1012126908@qq.com
 * desc:     登录和注册界面
 */
public class LoginRegisterFragment extends BaseFragment<LoginRegisterViewModel, LoginRegisterModel> {

    private FragmentLoginBinding mDataBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView() {
        mDataBinding = (FragmentLoginBinding) getViewDataBinding();

        ((AppCompatActivity) getActivity()).setSupportActionBar(mDataBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDataBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    @Override
    public int getBR() {
        return BR.loginModel;
    }
}
