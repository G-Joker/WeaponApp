package com.weapon.joker.app.mine.login;

import com.weapon.joker.app.mine.BR;
import com.weapon.joker.app.mine.R;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

/**
 * class：   com.weapon.joker.app.mine.login.LoginRegisterFragment
 * author：  xiaweizi
 * date：    2017/10/14 20:54
 * e-mail:   1012126908@qq.com
 * desc:     登录和注册界面
 */
public class LoginRegisterFragment extends BaseFragment<LoginRegisterViewModel, LoginRegisterModel> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView() {

    }

    @Override
    public int getBR() {
        return BR.loginModel;
    }
}
