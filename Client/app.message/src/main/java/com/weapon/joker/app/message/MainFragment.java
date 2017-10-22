package com.weapon.joker.app.message;

import android.content.Intent;

import com.weapon.joker.lib.mvvm.common.BaseFragment;
import com.weapon.joker.lib.middleware.PublicActivity;
import com.weapon.joker.lib.net.event.PushNewsEvent;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
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
        // 注册消息接收事件
        JMessageClient.registerEventReceiver(this, 200);
    }

    @Override
    public void onDestroy() {
        // 取消消息接收事件的注册
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    /**
     * 接收消息事件
     * 目前只支持文字消息，后面再进行优化
     *
     * @param event 消息事件
     */
    public void onEventMainThread(MessageEvent event) {
        getViewModel().updateOfficeData(((TextContent) event.getMessage().getContent()).getText());
    }

    /**
     * 点击通知
     *
     * @param event
     */
    public void onEvent(NotificationClickEvent event) {
        String userName = event.getMessage().getFromUser().getUserName();
        String displayName = event.getMessage().getFromUser().getDisplayName();
        Intent intent = new Intent(getContext(), PublicActivity.class);
        intent.putExtra("user_name", userName);
        intent.putExtra("display_name", displayName);
        PublicActivity.startActivity(getActivity(), "com.weapon.joker.app.message.office.OfficeFragment", intent);
    }
}