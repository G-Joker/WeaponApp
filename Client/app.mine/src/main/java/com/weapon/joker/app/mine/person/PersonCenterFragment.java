package com.weapon.joker.app.mine.person;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.weapon.joker.app.mine.BR;
import com.weapon.joker.app.mine.R;
import com.weapon.joker.app.mine.databinding.FragmentPersonCenterBinding;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.mine.person.PersonCenterFragment
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/13
 *     desc   :
 * </pre>
 */

public class PersonCenterFragment extends BaseFragment<PersonCenterViewModel, PersonCenterModel> implements PersonCenterContact.View {

    private FragmentPersonCenterBinding mDataBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_person_center;
    }

    @Override
    public void initView() {
        mDataBinding = ((FragmentPersonCenterBinding) getViewDataBinding());
        setToolbar();
        getViewModel().setHeaderData();
    }

    /**
     * Toolbar 相关设置
     */
    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mDataBinding.toolbar);
        // 设置 toolbar 具有返回按钮
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDataBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    @Override
    public int getBR() {
        return BR.personModel;
    }
}
