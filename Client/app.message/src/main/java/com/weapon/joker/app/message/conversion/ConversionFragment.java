package com.weapon.joker.app.message.conversion;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.conversion.ConversionFragment
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/20
 *     desc   :
 * </pre>
 */

public class ConversionFragment extends BaseFragment<ConversionViewModel, ConversionModel> implements ConversionContact.View {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_conversion;
    }

    @Override
    public void initView() {
        getViewModel().init();
    }

    @Override
    public int getBR() {
        return BR.conversionModel;
    }
}
