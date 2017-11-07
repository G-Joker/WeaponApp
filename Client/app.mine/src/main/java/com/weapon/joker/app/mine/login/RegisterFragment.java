package com.weapon.joker.app.mine.login;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.weapon.joker.app.mine.BR;
import com.weapon.joker.app.mine.R;
import com.weapon.joker.app.mine.databinding.FragmentRegisterBinding;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

/**
 * class：   com.weapon.joker.app.mine.login.RegisterFragment
 * author：  xiaweizi
 * date：    2017/10/14 20:54
 * e-mail:   1012126908@qq.com
 * desc:     注册界面
 */
public class RegisterFragment extends BaseFragment<RegisterViewModel, LoginRegisterModel> implements LoginRegisterContact.View{

    private FragmentRegisterBinding mDataBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void initView() {
        mDataBinding = (FragmentRegisterBinding) getViewDataBinding();
        setToolbar();
    }


    /**
     * Toolbar 相关设置
     */
    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mDataBinding.toolbar);
        // 设置 toolbar 具有返回按钮
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
        return BR.registerModel;
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
        // 判断用户名是否为空
        if (TextUtils.isEmpty(getViewModel().userName)) {
            mDataBinding.tilUserName.setError(getString(R.string.mine_username_null));
            return false;
        }
        // 判断密码是否为空
        if (TextUtils.isEmpty(getViewModel().password)) {
            mDataBinding.tilPassword.setError(getString(R.string.mine_password_null));
            return false;
        }
        // 判断两次输入的密码是否相同
        if (!TextUtils.equals(getViewModel().passwordAgain, getViewModel().password)) {
            mDataBinding.tilPasswordAgain.setError(getString(R.string.mine_password_different));
            return false;
        }

        return true;
    }
}
