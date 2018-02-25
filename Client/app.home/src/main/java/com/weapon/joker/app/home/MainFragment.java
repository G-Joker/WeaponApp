package com.weapon.joker.app.home;


import android.graphics.drawable.AnimationDrawable;

import com.weapon.joker.app.home.databinding.FragmentHomeBinding;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

/**
 * HomeFragment 首页 Fragment
 * author:张冠之
 * time: 2017/9/10 下午2:17
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class MainFragment extends BaseFragment<HomeViewModel, HomeModel> implements HomeContact.View{

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        getViewModel().requestRecommandData();
        AnimationDrawable anim = (AnimationDrawable) ((FragmentHomeBinding)getViewDataBinding()).imgLoading.getDrawable();
        anim.start();
    }

    @Override
    public int getBR() {
        return BR.viewModel;
    }
}
