package com.weapon.joker.app.mine;

import android.content.Intent;

import com.tencent.tauth.Tencent;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;

/**
 * MainFragment 我的 Fragment
 * author:张冠之
 * time: 2017/9/10 下午2:43
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class MainFragment extends BaseFragment<MineViewModel, MineModel> implements MineContact.View {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        JMessageClient.registerEventReceiver(this);
        getViewModel().initShare();
    }

    @Override
    public int getBR() {
        return com.weapon.joker.app.mine.BR.model;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, getViewModel().getIUiListener());
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().updateUserInfo();
    }

    @Override
    public void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    public void onEventMainThread(LoginStateChangeEvent event) {
        getViewModel().updateUserInfo();
    }

}
