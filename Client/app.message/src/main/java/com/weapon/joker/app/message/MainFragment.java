package com.weapon.joker.app.message;

import android.support.v7.app.AppCompatActivity;

import com.weapon.joker.app.message.databinding.FragmentMessageBinding;
import com.weapon.joker.lib.mvvm.common.BaseFragment;
import com.weapon.joker.lib.net.event.PushNewsEvent;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
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

    private FragmentMessageBinding mDataBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void initView() {
        mDataBinding = ((FragmentMessageBinding) getViewDataBinding());
        getViewModel().getPostNews();
        setToolbar();
    }

    @Override
    public int getBR() {
        return com.weapon.joker.app.message.BR.messageModel;
    }

    /**
     * Toolbar 相关设置
     */
    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mDataBinding.toolbar);
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
     * 切换悬浮菜单按钮
     */
    @Override
    public void toggleFloatingMenu() {
        // 延迟500ms关闭悬浮按钮
        mDataBinding.famStartChat.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDataBinding.famStartChat.toggle();
            }
        }, 500);
    }

    /**
     * 当接收到推送会发送 {@link PushNewsEvent}
     *
     * @param event 收到推送的事件
     */
    public void onEvent(PushNewsEvent event) {
        if (event != null && isAdded() && getViewModel() != null) {
            getViewModel().getPostNews();
        }
    }


    /**
     * 接收消息事件
     * 目前只支持文字消息，后面再进行优化
     *
     * @param event 消息事件
     */
    public void onEventMainThread(MessageEvent event) {
        Message message = event.getMessage();
        if (message.getContent() instanceof TextContent) {
            getViewModel().updateOfficeData(((TextContent) message.getContent()).getText(), 1);
        }
    }


    /**
     * 类似MessageEvent事件的接收，上层在需要的地方增加OfflineMessageEvent事件的接收
     * 即可实现离线消息的接收。
     **/
    public void onEventMainThread(OfflineMessageEvent event) {
        //获取事件发生的会话对象
        Conversation conversation = event.getConversation();
        List<Message> newMessageList = event.getOfflineMessageList();//获取此次离线期间会话收到的新消息列表
        if (newMessageList != null && newMessageList.size() > 0) {
            Message message = newMessageList.get(0);
            if (message.getContent() instanceof TextContent) {
                String content = ((TextContent) message.getContent()).getText();
                getViewModel().updateOfficeData(content, newMessageList.size());
            }
        }
    }
}