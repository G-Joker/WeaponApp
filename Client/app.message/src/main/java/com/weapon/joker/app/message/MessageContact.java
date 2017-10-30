package com.weapon.joker.app.message;

import com.weapon.joker.lib.mvvm.common.BaseModel;
import com.weapon.joker.lib.mvvm.common.BaseView;
import com.weapon.joker.lib.mvvm.common.BaseViewModel;

/**
 * MessageContact 消息接口管理类
 * author:张冠之
 * time: 2017/10/12 下午1:40
 * e-mail: guanzhi.zhang@sojex.cn
 */

interface MessageContact {
    interface View extends BaseView{
        void toggleFloatingMenu();
    }

    abstract class ViewModel extends BaseViewModel<View,Model>{
        abstract void getPostNews();
    }

    abstract class Model extends BaseModel<ViewModel>{

    }
}
