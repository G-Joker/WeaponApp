package com.weapon.joker.app.mine.person;

import com.weapon.joker.lib.mvvm.common.BaseModel;
import com.weapon.joker.lib.mvvm.common.BaseView;
import com.weapon.joker.lib.mvvm.common.BaseViewModel;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.mine.person.PersonCenterContact
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/13
 *     desc   : 个人中心管理类
 * </pre>
 */

interface PersonCenterContact {

    interface View extends BaseView {

    }

    abstract class ViewModel extends BaseViewModel<View, Model> {

    }

    abstract class Model extends BaseModel<ViewModel> {

    }
}
