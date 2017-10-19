package com.weapon.joker.app.message.office;

import com.weapon.joker.lib.mvvm.common.BaseModel;
import com.weapon.joker.lib.mvvm.common.BaseView;
import com.weapon.joker.lib.mvvm.common.BaseViewModel;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.office.OfficeContact
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/18
 *     desc   : 官方详细管理
 * </pre>
 */

public interface OfficeContact {

    interface View extends BaseView {
        void scrollToPosition(int position);
        void refreshFinish(int refreshResult);
    }

    abstract class ViewModel extends BaseViewModel<View, Model> {

    }
    abstract class Model extends BaseModel<ViewModel> {

    }
}
