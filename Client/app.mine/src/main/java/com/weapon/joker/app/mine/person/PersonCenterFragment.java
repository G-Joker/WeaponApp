package com.weapon.joker.app.mine.person;

import com.weapon.joker.app.mine.BR;
import com.weapon.joker.app.mine.R;
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

public class PersonCenterFragment extends BaseFragment<PersonCenterViewModel, PersonCenterModel> implements PersonCenterContact.View{

    @Override
    public int getLayoutId() {
        return R.layout.fragment_person_center;
    }

    @Override
    public void initView() {

    }

    @Override
    public int getBR() {
        return BR.personModel;
    }
}
