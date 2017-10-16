package com.weapon.joker.app.mine.login;

import android.databinding.Bindable;
import android.view.View;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.weapon.joker.app.mine.R;
import com.weapon.joker.lib.net.BaseObserver;
import com.weapon.joker.lib.net.model.BaseResModel;
import com.weapon.joker.lib.net.model.RegisterModel;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.mine.login.RegisterViewModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/16
 *     desc   : 注册 ViewModel
 * </pre>
 */

public class RegisterViewModel extends LoginRegisterViewModel {

    /**
     * 再输入一次密码
     */
    @Bindable
    public String passwordAgain;

    public void onRegisterClick(View view) {
        if (!getView().checkInputContent()) {
            return;
        }
        requestRegister(userName, password);
    }

    /*============================== 网络请求 ===================================*/
    @Override
    void requestRegister(String userName, String password) {
        getModel().register(userName, password).subscribe(new BaseObserver<RegisterModel>() {
            @Override
            protected void onSuccess(RegisterModel entry) throws Exception {
                if (entry != null) {
                    if (entry.status == BaseResModel.REQUEST_SUCCESS && entry.data != null) {
                        registerSuccess(entry);
                    } else {
                        registerFailed(entry.desc);
                    }
                } else {
                    registerFailed(getContext().getString(R.string.public_net_error));
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                registerFailed(getContext().getString(R.string.public_net_error));
            }
        });
    }


    /*============================== 网络请求回调 ===================================*/
    /**
     * 注册失败
     * @param desc 注册失败描述
     */
    private void registerFailed(String desc) {
        Toast.makeText(getContext(), desc, Toast.LENGTH_SHORT).show();
    }

    /**
     * 注册成功
     * @param entry 注册成功返回的实体 bean
     */
    private void registerSuccess(RegisterModel entry) {
        Toast.makeText(getContext(), R.string.mine_register_repeat, Toast.LENGTH_SHORT).show();
        MobclickAgent.onEvent(getContext().getApplicationContext(), "mine_register", entry.data.user);
        getView().finish();
    }
}
