package com.weapon.joker.app.message.single;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;
import com.weapon.joker.lib.middleware.utils.LogUtils;
import com.weapon.joker.lib.mvvm.command.Action0;
import com.weapon.joker.lib.mvvm.command.ReplyCommand;
import com.weapon.joker.lib.view.pullrefreshload.PullToRefreshLayout;

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
 *     class  : com.weapon.joker.app.message.office.SingleViewModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/18
 *     desc   :
 * </pre>
 */

public class SingleViewModel extends SingleContact.ViewModel {

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

    /**
     * 进入界面初始，获取消息记录
     * @param userName
     */
    public void init(String userName) {
        mConversation = Conversation.createSingleConversation(userName);
        if (mConversation == null) {
            getView().finish();
        }
        msgCount = mConversation.getAllMessage().size();
        List<Message> messagesFromNewest = mConversation.getMessagesFromNewest(curCount, LIM_COUNT);
        curCount = messagesFromNewest.size();
        Collections.reverse(messagesFromNewest);
        for (Message message : messagesFromNewest) {
            MessageDirect direct = message.getDirect();
            String text = ((TextContent) message.getContent()).getText();
            if (direct == MessageDirect.send) {
                addSendMessage(text);
            } else {
                addReceiverMessage(text);
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
                    MobclickAgent.onEvent(getContext().getApplicationContext(), "send_message", sendContent);
                    addSendMessage(sendContent);
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

    /**===========================RecyclerView相关的初始化=====================================*/
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
                    String text = ((TextContent) message.getContent()).getText();
                    if (direct == MessageDirect.send) {
                        addSendMessage(0, text);
                    } else {
                        addReceiverMessage(0, text);
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
     * 添加发送消息
     * @param sendContent 发送的消息内容
     */
    private void addSendMessage(String sendContent) {
        MessageItemViewModel sendMessage = new MessageItemViewModel();
        sendMessage.type = MessageItemViewModel.MSG_SEND;
        sendMessage.content = sendContent;
        items.add(sendMessage);
    }

    /**
     * 添加发送消息到指定的下标
     * @param index 下标
     * @param sendContent 发送的消息内容
     */
    private void addSendMessage(int index, String sendContent) {
        MessageItemViewModel sendMessage = new MessageItemViewModel();
        sendMessage.type = MessageItemViewModel.MSG_SEND;
        sendMessage.content = sendContent;
        items.add(index, sendMessage);
    }

    /**
     *  添加接收消息
     * @param receiverContent 接收的消息内容
     */
    private void addReceiverMessage(String receiverContent) {
        MessageItemViewModel receiverMessage = new MessageItemViewModel();
        receiverMessage.type = MessageItemViewModel.MSG_RECEIVER;
        receiverMessage.content = receiverContent;
        items.add(receiverMessage);
    }

    /**
     * 添加接收消息到指定的下标
     * @param index  下标
     * @param receiverContent 接收的消息内容
     */
    private void addReceiverMessage(int index, String receiverContent) {
        MessageItemViewModel receiverMessage = new MessageItemViewModel();
        receiverMessage.type = MessageItemViewModel.MSG_RECEIVER;
        receiverMessage.content = receiverContent;
        items.add(index, receiverMessage);
    }

    /**
     * 接收到消息
     *
     * @param content 消息
     */
    public void receiveMessage(String content) {
        addReceiverMessage(content);
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
