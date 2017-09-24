package com.weapon.joker.app.mine;

import android.widget.Toast;

import com.weapon.joker.lib.mvvm.common.BaseFragment;

/**
 * MainFragment 我的 Fragment
 * author:张冠之
 * time: 2017/9/10 下午2:43
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class MainFragment extends BaseFragment<MineViewModel,MineModel> implements MineContact.View{

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

    @Override
    public void TestType() {
        Toast.makeText(getActivity(),"接口调用成功",Toast.LENGTH_LONG).show();
    }
}
