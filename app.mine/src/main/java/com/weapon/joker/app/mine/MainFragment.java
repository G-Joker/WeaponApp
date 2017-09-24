package com.weapon.joker.app.mine;

import com.weapon.joker.lib.mvvm.common.BaseFragment;

/**
 * MainFragment 我的 Fragment
 * author:张冠之
 * time: 2017/9/10 下午2:43
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class MainFragment extends BaseFragment<MineViewModel,MineModel> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        getViewModel().init();
    }

    @Override
    public int getBR() {
        return BR.model;
    }
}
