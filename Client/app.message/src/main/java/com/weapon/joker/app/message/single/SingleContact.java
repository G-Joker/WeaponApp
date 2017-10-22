package com.weapon.joker.app.message.single;

import com.weapon.joker.lib.mvvm.common.BaseModel;
import com.weapon.joker.lib.mvvm.common.BaseView;
import com.weapon.joker.lib.mvvm.common.BaseViewModel;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.office.SingleContact
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/18
 *     desc   : 官方详细管理
 * </pre>
 */

public interface SingleContact {

    interface View extends BaseView {
        void scrollToPosition(int position);
        void refreshFinish(int refreshResult);
    }

    abstract class ViewModel extends BaseViewModel<View, Model> {

    }
    abstract class Model extends BaseModel<ViewModel> {

    }
}
