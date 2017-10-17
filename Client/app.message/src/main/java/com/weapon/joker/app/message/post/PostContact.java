package com.weapon.joker.app.message.post;

import com.weapon.joker.lib.mvvm.common.BaseModel;
import com.weapon.joker.lib.mvvm.common.BaseView;
import com.weapon.joker.lib.mvvm.common.BaseViewModel;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.post.PostContact
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/17
 *     desc   : 公告管理类
 * </pre>
 */

interface PostContact {
    interface View extends BaseView{
        void refreshFinish(int state);
    }
    abstract class ViewModel extends BaseViewModel<View, Model> {

    }

    abstract class Model extends BaseModel<ViewModel> {

    }

}
