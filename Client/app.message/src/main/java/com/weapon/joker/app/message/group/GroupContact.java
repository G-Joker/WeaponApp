package com.weapon.joker.app.message.group;

import com.weapon.joker.lib.mvvm.common.BaseModel;
import com.weapon.joker.lib.mvvm.common.BaseView;
import com.weapon.joker.lib.mvvm.common.BaseViewModel;

/**
 * class：   com.weapon.joker.app.message.group.GroupContact
 * author：  xiaweizi
 * date：    2017/10/22 11:56
 * e-mail:   1012126908@qq.com
 * desc:
 */
public interface GroupContact {
    interface View extends BaseView{
        void scrollToPosition(int position);
        void refreshFinish(int refreshResult);
    }
    abstract class ViewModel extends BaseViewModel<View, Model>{

    }

    abstract class Model extends BaseModel<ViewModel> {

    }
}
