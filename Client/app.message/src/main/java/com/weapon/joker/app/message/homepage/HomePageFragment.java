package com.weapon.joker.app.message.homepage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;
import com.weapon.joker.app.message.databinding.FragmentHomePageBinding;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.homepage.HomePageFragment
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/27
 *     desc   : 个人主页界面
 * </pre>
 */

public class HomePageFragment extends BaseFragment<HomePageViewModel, HomePageModel> implements HomePageContact.View {

    private FragmentHomePageBinding mDataBinding;
    private String mUserName;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void initView() {
        mDataBinding = ((FragmentHomePageBinding) getViewDataBinding());
        if (getActivity().getIntent() != null) {
            Intent intent = getActivity().getIntent();
            if (!TextUtils.isEmpty(intent.getStringExtra("user_name"))) {
                mUserName = intent.getStringExtra("user_name");
            }
        } else {
            finish();
        }
        getViewModel().init(mUserName);
        setToolbar();
    }

    /**
     * Toolbar 相关设置
     */
    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mDataBinding.toolbar);
        // 设置 toolbar 具有返回按钮
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        mDataBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    @Override
    public int getBR() {
        return BR.homePageModel;
    }
}
