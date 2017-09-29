package com.weapon.joker.app.mine;

import android.content.Intent;
import android.view.View;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.weapon.joker.app.mine.databinding.FragmentMineBinding;
import com.weapon.joker.app.stub.share.ShareParams;
import com.weapon.joker.lib.middleware.utils.JLog;
import com.weapon.joker.lib.middleware.utils.share.ShareView;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

/**
 * MainFragment 我的 Fragment
 * author:张冠之
 * time: 2017/9/10 下午2:43
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class MainFragment extends BaseFragment<MineViewModel, MineModel> implements MineContact.View, IUiListener {

    private ShareView mShareView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        ShareParams params = new ShareParams.Builder().setTitle(getActivity().getResources().getString(R.string.share_name))
                                                      .setDescription(getActivity().getResources().getString(R.string.share_desc))
                                                      .setAppUrl(getActivity().getResources().getString(R.string.share_url))
                                                      .setImgUrl(getActivity().getResources().getString(R.string.share_img_url))
                                                      .setResId(R.mipmap.ic_launcher_round)
                                                      .build();
        mShareView = new ShareView(getActivity(), params, this);

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
        Tencent.onActivityResultData(requestCode, resultCode, data, this);
        super.onActivityResult(requestCode, resultCode, data);
    }

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
}
