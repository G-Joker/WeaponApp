package com.weapon.joker.app.message;

import android.content.Intent;

import com.weapon.joker.lib.mvvm.common.BaseFragment;
import com.weapon.joker.lib.middleware.PublicActivity;
import com.weapon.joker.lib.net.event.PushNewsEvent;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
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
        getViewModel().updateOfficeData(((TextContent) event.getMessage().getContent()).getText(), 1);
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
        PublicActivity.startActivity(getActivity(), "com.weapon.joker.app.message.office.SingleFragment", intent);
    }

    /**
     类似MessageEvent事件的接收，上层在需要的地方增加OfflineMessageEvent事件的接收
     即可实现离线消息的接收。
     **/
    public void onEventMainThread(OfflineMessageEvent event) {
        //获取事件发生的会话对象
        Conversation conversation = event.getConversation();
        List<Message> newMessageList = event.getOfflineMessageList();//获取此次离线期间会话收到的新消息列表
        if (newMessageList != null && newMessageList.size() > 0) {
            String content = ((TextContent) newMessageList.get(0).getContent()).getText();
            getViewModel().updateOfficeData(content, newMessageList.size());
        }
    }
}