package com.weapon.joker.app.mine.login;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
public class LoginRegisterFragment extends BaseFragment<LoginRegisterViewModel, LoginRegisterModel> implements LoginRegisterContact.View{

    private FragmentLoginBinding mDataBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView() {
        mDataBinding = (FragmentLoginBinding) getViewDataBinding();
        setToolbar();
    }


    /**
     * Toolbar 相关设置
     */
    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mDataBinding.toolbar);
        // 设置 toolbar 具有返回按钮
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDataBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mDataBinding.tvRegisterNow.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public int getBR() {
        return BR.loginModel;
    }

    /**
     * 检测输入内容是否为空
     * @return true: 检测成功 false 检测失败
     */
    @Override
    public boolean checkInputContent() {
        // 充值输入框错误状态
        mDataBinding.tilUserName.setErrorEnabled(false);
        mDataBinding.tilPassword.setErrorEnabled(false);

        if (TextUtils.isEmpty(getViewModel().userName)) {
            mDataBinding.tilUserName.setError(getString(R.string.mine_username_null));
            return false;
        }
        if (TextUtils.isEmpty(getViewModel().password)) {
            mDataBinding.tilPassword.setError(getString(R.string.mine_password_null));
            return false;
        }

        return true;
    }
}
