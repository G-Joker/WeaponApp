package com.weapon.joker.app.message.office;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;
import com.weapon.joker.app.message.databinding.FragmentOfficeBinding;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.office.OfficeFragment
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/18
 *     desc   : 官方消息界面
 * </pre>
 */

public class OfficeFragment extends BaseFragment<OfficeViewModel, OfficeModel> implements OfficeContact.View {

    private FragmentOfficeBinding mDataBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_office;
    }

    @Override
    public void initView() {
        mDataBinding = ((FragmentOfficeBinding) getViewDataBinding());
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
        return BR.officeModel;
    }
}
