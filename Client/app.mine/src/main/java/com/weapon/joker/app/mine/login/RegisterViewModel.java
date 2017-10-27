package com.weapon.joker.app.mine.login;

import android.databinding.Bindable;
import android.view.View;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.weapon.joker.app.mine.R;
import com.weapon.joker.lib.middleware.utils.AlertDialogFactory;
import com.weapon.joker.lib.net.JMessageCallBack;

import cn.jpush.im.android.api.JMessageClient;

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

    @Override
    public void onRegisterClick(View view) {
        if (!getView().checkInputContent()) {
            return;
        }
        requestRegister(userName, password);
    }

    /*============================== 网络请求 ===================================*/
    @Override
    void requestRegister(String userName, String password) {
        if (mLoadingDialog == null) {
            mLoadingDialog = AlertDialogFactory.createLoadingDialog(getContext(), "正在注册");
        }
        mLoadingDialog.show();
        // 使用 JMessage 的接口进行登录
        JMessageClient.register(userName, password, new JMessageCallBack() {
            @Override
            public void onSuccess() {
                registerSuccess();
            }

            @Override
            public void onFailed(int status, String desc) {
                registerFailed(desc);
            }
        });
    }


    /*============================== 网络请求回调 ===================================*/
    /**
     * 注册失败
     * @param desc 注册失败描述
     */
    private void registerFailed(String desc) {
        dismissDialog();
        Toast.makeText(getContext(), desc, Toast.LENGTH_SHORT).show();
    }

    /**
     * 注册成功
     */
    private void registerSuccess() {
        dismissDialog();
        Toast.makeText(getContext(), R.string.mine_register_repeat, Toast.LENGTH_SHORT).show();
        MobclickAgent.onEvent(getContext().getApplicationContext(), "mine_register", userName);
        getView().finish();
    }

}
