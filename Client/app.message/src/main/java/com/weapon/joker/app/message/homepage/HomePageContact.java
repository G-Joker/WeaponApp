package com.weapon.joker.app.message.homepage;

import com.weapon.joker.lib.mvvm.common.BaseModel;
import com.weapon.joker.lib.mvvm.common.BaseView;
import com.weapon.joker.lib.mvvm.common.BaseViewModel;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.homepage.HomePageContact
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/27
 *     desc   :
 * </pre>
 */

public interface HomePageContact {
    interface View extends BaseView{

    }

    abstract class ViewModel extends BaseViewModel<View, Model> {

    }

    abstract class Model extends BaseModel<ViewModel>{

    }
}
