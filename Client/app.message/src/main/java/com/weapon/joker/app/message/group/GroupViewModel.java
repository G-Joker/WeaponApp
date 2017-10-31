package com.weapon.joker.app.message.group;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;
import com.weapon.joker.app.message.single.MessageItemViewModel;
import com.weapon.joker.lib.middleware.utils.LogUtils;
import com.weapon.joker.lib.middleware.utils.Util;
import com.weapon.joker.lib.mvvm.command.Action0;
import com.weapon.joker.lib.mvvm.command.ReplyCommand;
import com.weapon.joker.lib.view.pullrefreshload.PullToRefreshLayout;

import java.util.Collections;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/**
 * class：   Client
 * author：  xiaweizi
 * date：    2017/10/22 11:57
 * e-mail:   1012126908@qq.com
 * desc:
 */
public class GroupViewModel extends GroupContact.ViewModel {

    /**
     * 默认群 ID
     */
    private static final long GROUP_ID = 23349803;
    /**
     * 每次获取聊天记录的数量
     */
    private static final int LIM_COUNT = 5;
    /**
     * 消息最长时间间隔(5分钟)
     */
    private static final long TIME_INTERVAL = 1000 * 60 * 5;
    /**
     * 当前回话
     */
    private Conversation mConversation;
    /**
     * 当前消息的数量
     */
    private int curCount = 0;
    /**
     * 消息发送的内容
     */
    @Bindable
    public String sendContent;
    /**
     * 最新消息的时间
     */
    private long latestMsgTime = 0;

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
        notifyPropertyChanged(BR.sendContent);
    }

    public void init() {
        mConversation = Conversation.createGroupConversation(GROUP_ID);
        if (mConversation == null) {
            getView().finish();
            return;
        }
        List<Message> messagesFromNewest = mConversation.getMessagesFromNewest(curCount, LIM_COUNT);
        curCount = messagesFromNewest.size();
        Collections.reverse(messagesFromNewest);
        for (Message message : messagesFromNewest) {
            MessageDirect direct = message.getDirect();
            if (direct == MessageDirect.send) {
                addSendMessage(message);
            } else {
                addReceiverMessage(message);
            }
        }
    }

    public void sendMessageToOffice(View view) {
        if (TextUtils.isEmpty(sendContent)) {
            Toast.makeText(getContext(), "发送的内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        LogUtils.i("Office", "content:\t" + sendContent);
        final Message message = mConversation.createSendTextMessage(sendContent);
        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int status, String desc) {
                if (status == 0) {
                    addSendMessage(message);
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

    /**
     * 添加发送消息
     */
    private void addSendMessage(Message sendMsg) {
        checkAddMsgData(sendMsg, false);
        MessageItemViewModel sendMessage = new MessageItemViewModel(sendMsg, MessageItemViewModel.MSG_SEND);
        items.add(sendMessage);
    }

    /**
     * 添加发送消息到指定的下标
     */
    private void addSendMessageAtFirst(Message sendMsg) {
        MessageItemViewModel sendMessage = new MessageItemViewModel(sendMsg, MessageItemViewModel.MSG_SEND);
        List<MessageItemViewModel> temp = new ObservableArrayList<>();
        temp.add(sendMessage);
        temp.addAll(items);
        items.clear();
        items.addAll(temp);
        checkAddMsgData(sendMsg, true);
    }

    /**
     * 添加接收消息
     */
    private void addReceiverMessage(Message receiverMsg) {
        checkAddMsgData(receiverMsg, false);
        MessageItemViewModel receiverMessage = new MessageItemViewModel(receiverMsg, MessageItemViewModel.MSG_RECEIVER);
        items.add(receiverMessage);
    }

    /**
     * 添加接收消息到指定的下标
     */
    private void addReceiverMessageAtFirst(Message receiverMsg) {
        MessageItemViewModel receiverMessage = new MessageItemViewModel(receiverMsg, MessageItemViewModel.MSG_RECEIVER);
        List<MessageItemViewModel> temp = new ObservableArrayList<>();
        temp.add(receiverMessage);
        temp.addAll(items);
        items.clear();
        items.addAll(temp);
        checkAddMsgData(receiverMsg, true);
    }

    /**
     * 检测是否要添加时间轴数据
     *
     * @param sendMsg
     * @param isFirst 是否插入到数据之前
     */
    private void checkAddMsgData(Message sendMsg, boolean isFirst) {
        long createTime = sendMsg.getCreateTime();
        if (isNeedAddMsgData(createTime, latestMsgTime)) {
            addMsgData(createTime, isFirst);
        }
        latestMsgTime = createTime;
    }

    /**
     * 添加发送消息时间
     *
     * @param time    消息发送时间
     * @param isFirst 是否从头插入
     */
    private void addMsgData(long time, boolean isFirst) {
        String timeForShow = Util.getTimeForShowDetail(time);
        MessageItemViewModel dataMessage = new MessageItemViewModel(timeForShow);
        if (isFirst) {
            items.add(0, dataMessage);
        } else {
            items.add(dataMessage);
        }
    }

    /**
     * @param curTime 当前时间
     * @param preTime 之前是间
     * @return 是否需要添加消息时间
     */
    private boolean isNeedAddMsgData(long curTime, long preTime) {
        return Math.abs(curTime - preTime) >= TIME_INTERVAL;
    }

    /**
     * 接收到消息
     */
    public void receiveMessage(MessageEvent event) {
        if (event == null) {
            return;
        }
        Message message = event.getMessage();
        switch (message.getContentType()) {
            case text:
                // 处理文字消息
                Object targetInfo = message.getTargetInfo();
                if (targetInfo instanceof GroupInfo) {
                    GroupInfo groupInfo = (GroupInfo) targetInfo;
                    if (groupInfo.getGroupID() == GROUP_ID) {
                        addReceiverMessage(message);
                        getView().scrollToPosition(items.size() - 1);
                    }
                }
            default:
                LogUtils.i("office", message.getFromType());
                break;
        }
    }

    /**
     * 清除所有消息
     */
    public void deleteAllMessage() {
        mConversation.deleteAllMessage();
        items.clear();
    }

    /**
     * ===========================RecyclerView相关的初始化=====================================
     */
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
                        addSendMessageAtFirst(message);
                    } else {
                        addReceiverMessageAtFirst(message);
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
                itemBinding.set(BR.msgModel, R.layout.item_group_msg_send);
            } else if (item.type == MessageItemViewModel.MSG_RECEIVER) {
                itemBinding.set(BR.msgModel, R.layout.item_group_msg_receiver);
            } else {
                itemBinding.set(BR.msgModel, R.layout.item_message_data);
            }
        }
    };
}
