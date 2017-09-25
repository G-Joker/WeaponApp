package com.weapon.joker.app.mine;

import android.content.Intent;
import android.view.View;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.weapon.joker.app.mine.databinding.FragmentMineBinding;
import com.weapon.joker.lib.middleware.utils.JLog;
import com.weapon.joker.lib.middleware.view.ShareView;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

/**
 * MainFragment 我的 Fragment
 * author:张冠之
 * time: 2017/9/10 下午2:43
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class MainFragment extends BaseFragment<MineViewModel,MineModel> implements MineContact.View{

    private ShareView mShareView;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        mShareView = new ShareView(getActivity());
        getViewModel().init();
        FragmentMineBinding binding = (FragmentMineBinding) getViewDataBinding();
        binding.tvMineShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareView.show();
            }
        });
    }

    @Override
    public int getBR() {
        return com.weapon.joker.app.mine.BR.model;
    }

    @Override
    public void TestType() {
//        Toast.makeText(getActivity(),"接口调用成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                JLog.i("shareTo-->" + "onComplete:\t" + o.toString());
            }

            @Override
            public void onError(UiError uiError) {
                JLog.i("shareTo-->" + "UiError:\t" + uiError.errorMessage);
            }

            @Override
            public void onCancel() {
                JLog.i("shareTo--->" + "onCancel");
            }
        });
        super.onActivityResult(requestCode, resultCode, data);
    }
}
