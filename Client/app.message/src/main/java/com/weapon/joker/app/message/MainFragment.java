package com.weapon.joker.app.message;

import com.weapon.joker.lib.mvvm.common.BaseFragment;
import com.weapon.joker.lib.net.event.PushNewsEvent;

import cn.jpush.im.android.eventbus.EventBus;

/**
 * MessageFragment 消息 Fragment
 * author:张冠之
 * time: 2017/9/10 下午2:28
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class MainFragment extends BaseFragment<MessageViewModel, MessageModel> implements MessageContact.View {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void initView() {
        getViewModel().getPostNews();
    }

    @Override
    public int getBR() {
        return BR.messageModel;
    }

    public void onEvent(PushNewsEvent event) {
        if (event != null && isAdded() && getViewModel() != null) {
            getViewModel().getPostNews();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().getPostNews();
    }
}