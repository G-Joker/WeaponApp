package com.weapon.joker.app.message;

import com.weapon.joker.lib.mvvm.common.BaseFragment;
import com.weapon.joker.lib.net.bean.MessageBean;

/**
 * MessageFragment 消息 Fragment
 * author:张冠之
 * time: 2017/9/10 下午2:28
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class MainFragment extends BaseFragment<MessageViewModel,MessageModel> implements MessageContact.View{

    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void initView() {
        getViewModel().requestData();
    }

    @Override
    public int getBR() {
        return com.weapon.joker.app.message.BR.model;
    }

    @Override
    public void loadSuccess(MessageBean bean) {
        bean.show();
    }
}