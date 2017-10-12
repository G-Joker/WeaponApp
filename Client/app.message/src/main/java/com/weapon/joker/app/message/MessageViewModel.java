package com.weapon.joker.app.message;

import android.view.View;

import com.weapon.joker.lib.middleware.utils.LogUtils;
import com.weapon.joker.lib.net.BaseObserver;
import com.weapon.joker.lib.net.bean.MessageBean;

/**
 * MessageViewModel 消息的VM
 * author:张冠之
 * time: 2017/10/12 下午1:45
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class MessageViewModel extends MessageContact.ViewModel{

    @Override
    void requestData() {
        getModel().loadData().subscribe(new BaseObserver<MessageBean>() {
            @Override
            protected void onSuccess(MessageBean entry) throws Exception {
                getView().loadSuccess(entry);
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                LogUtils.logi("http error");
            }
        });
    }

    @Override
    public void onTextClick(View view) {
        requestData();
    }


}
