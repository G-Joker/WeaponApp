package com.weapon.joker.app.message.office;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;
import com.weapon.joker.lib.middleware.utils.LogUtils;
import com.weapon.joker.lib.mvvm.command.Action0;
import com.weapon.joker.lib.mvvm.command.ReplyCommand;
import com.weapon.joker.lib.mvvm.pullrefreshload.PullToRefreshLayout;

import java.util.Collections;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.office.OfficeViewModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/18
 *     desc   :
 * </pre>
 */

public class OfficeViewModel extends OfficeContact.ViewModel {

    /**
     * 每次获取聊天记录的数量
     */
    private static final int LIM_COUNT = 5;
    /**
     * 消息发送的内容
     */
    @Bindable
    public String sendContent;
    /**
     * 当前回话
     */
    private Conversation mConversation;
    /**
     * 消息总数
     */
    private int msgCount;
    /**
     * 当前消息的数量
     */
    private int curCount = 0;


    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
        notifyPropertyChanged(BR.sendContent);
    }

    public void init(String userName) {
        mConversation = Conversation.createSingleConversation(userName);
        msgCount = mConversation.getAllMessage().size();
        List<Message> messagesFromNewest = mConversation.getMessagesFromNewest(curCount, LIM_COUNT);
        curCount = messagesFromNewest.size();
        Collections.reverse(messagesFromNewest);
        for (Message message : messagesFromNewest) {
            MessageDirect direct = message.getDirect();
            if (direct == MessageDirect.send) {
                MessageItemViewModel send = new MessageItemViewModel();
                send.type = MessageItemViewModel.MSG_SEND;
                send.content = ((TextContent) message.getContent()).getText();
                items.add(send);
            } else {
                MessageItemViewModel receiver = new MessageItemViewModel();
                receiver.type = MessageItemViewModel.MSG_RECEIVER;
                receiver.content = ((TextContent) message.getContent()).getText();
                items.add(receiver);
            }
        }
    }

    /**
     * 给官方客服发送文本消息
     */
    public void sendMessageToOffice(View view) {
        if (TextUtils.isEmpty(sendContent)) {
            Toast.makeText(getContext(), "发送的内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        LogUtils.i("Office", "content:\t" + sendContent);
        Message message = mConversation.createSendTextMessage(sendContent);
        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int status, String desc) {
                if (status == 0) {
                    // 消息发送成功
                    MessageItemViewModel send = new MessageItemViewModel();
                    send.type = MessageItemViewModel.MSG_SEND;
                    send.content = sendContent;
                    items.add(send);
                    ++curCount;
                    setSendContent("");
                    getView().scrollToPosition(items.size() - 1);
                } else {
                    // 消息发送失败
                    Toast.makeText(getContext(), desc, Toast.LENGTH_SHORT).show();
                }
            }
        });
        JMessageClient.sendMessage(message);
    }


    public final ReplyCommand onRefreshCommand = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            List<Message> messagesFromNewest = mConversation.getMessagesFromNewest(curCount, LIM_COUNT);
            int size = messagesFromNewest.size();
            if (size == 0) {
                Toast.makeText(getContext(), "没有更多消息了", Toast.LENGTH_SHORT).show();
                getView().refreshFinish(PullToRefreshLayout.FAIL);
            } else {
                for (Message message : messagesFromNewest) {
                    MessageDirect direct = message.getDirect();
                    if (direct == MessageDirect.send) {
                        MessageItemViewModel send = new MessageItemViewModel();
                        send.type = MessageItemViewModel.MSG_SEND;
                        send.content = ((TextContent) message.getContent()).getText();
                        items.add(0, send);
                    } else {
                        MessageItemViewModel receiver = new MessageItemViewModel();
                        receiver.type = MessageItemViewModel.MSG_RECEIVER;
                        receiver.content = ((TextContent) message.getContent()).getText();
                        items.add(0, receiver);
                    }
                }
                curCount += LIM_COUNT;
                getView().scrollToPosition(0);
                getView().refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }
    });


    public final ObservableList<MessageItemViewModel> items = new ObservableArrayList<>();
    public final BindingRecyclerViewAdapter.ItemIds<MessageItemViewModel> itemIds
            = new BindingRecyclerViewAdapter.ItemIds<MessageItemViewModel>() {
        @Override
        public long getItemId(int position, MessageItemViewModel item) {
            return position;
        }
    };

    public final OnItemBind<MessageItemViewModel> multiItems = new OnItemBind<MessageItemViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, MessageItemViewModel item) {
            if (item.type == MessageItemViewModel.MSG_SEND) {
                itemBinding.set(BR.msgModel, R.layout.item_office_msg_send);
            } else {
                itemBinding.set(BR.msgModel, R.layout.item_office_msg_receiver);
            }
        }
    };

    /**
     * 接收到消息
     *
     * @param content 消息
     */
    public void receiveMessage(String content) {
        MessageItemViewModel send = new MessageItemViewModel();
        send.type = MessageItemViewModel.MSG_RECEIVER;
        send.content = content;
        items.add(send);
        getView().scrollToPosition(items.size() - 1);
    }

    /**
     * 清除所有消息
     */
    public void deleteAllMessage() {
        mConversation.deleteAllMessage();
        items.clear();
    }
}
