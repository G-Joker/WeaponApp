package com.weapon.joker.app.message;

import com.weapon.joker.lib.mvvm.common.BaseModel;
import com.weapon.joker.lib.mvvm.common.BaseView;
import com.weapon.joker.lib.mvvm.common.BaseViewModel;
import com.weapon.joker.lib.net.bean.MessageBean;

import io.reactivex.Observable;

/**
 * MessageContact 消息接口管理类
 * author:张冠之
 * time: 2017/10/12 下午1:40
 * e-mail: guanzhi.zhang@sojex.cn
 */

interface MessageContact {
    interface View extends BaseView{
        void loadSuccess(MessageBean bean);
    }

    abstract class ViewModel extends BaseViewModel<View,Model>{
       abstract void requestData();
       abstract void onTextClick(android.view.View view);
    }

    abstract class Model extends BaseModel<ViewModel>{
        abstract Observable<MessageBean> loadData();
    }
}
