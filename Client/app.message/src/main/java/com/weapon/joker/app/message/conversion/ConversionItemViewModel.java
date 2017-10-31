package com.weapon.joker.app.message.conversion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.lib.middleware.PublicActivity;
import com.weapon.joker.lib.middleware.utils.Util;
import com.weapon.joker.lib.view.CustomPopWindow;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.conversion.ConversionItemViewModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/20
 *     desc   : 单独聊天界面和群聊
 * </pre>
 */

public class ConversionItemViewModel extends BaseObservable {

    /**
     * 单聊类型
     */
    private static final int TYPE_SINGLE = 1;
    /**
     * 群聊类型
     */
    private static final int TYPE_GROUP = 2;
    /**
     * 显示的 name
     */
    @Bindable
    public String displayName;
    /**
     * 最新内容
     */
    @Bindable
    public String lastContent;

    /**
     * 最新消息时间
     */
    @Bindable
    public String lastTime;

    /**
     * 用户名
     */
    public String userName;
    /**
     * 未读消息数量
     */
    @Bindable
    public int unReadNum = 0;
    /**
     * 红点是否可见
     */
    @Bindable
    public boolean redVisible = false;
    /**
     * 头像
     */
    @Bindable
    public File avatarFile;
    /**
     * 图片的类型
     * {@link #TYPE_SINGLE}
     * {@link #TYPE_GROUP}
     */
    @Bindable
    public int type;

    private Context mContext;
    private Conversation mConversion;

    public void setRedVisible(boolean redVisible) {
        this.redVisible = redVisible;
        notifyPropertyChanged(BR.redVisible);
    }

    public void setUnReadNum(int unReadNum) {
        this.unReadNum = unReadNum;
        notifyPropertyChanged(BR.unReadNum);
    }

    public void setAvatarFile(File avatarFile) {
        this.avatarFile = avatarFile;
        notifyPropertyChanged(BR.avatarFile);
    }

    public void setType(int type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    public ConversionItemViewModel(Context context, Conversation conversation, OnDeleteConversionListener listener) {
        mContext = context;
        mConversion = conversation;
        mListener = listener;
        // 获取未读消息数量
        int unReadMsgCnt = conversation.getUnReadMsgCnt();
        Message latestMessage = conversation.getLatestMessage();
        Object targetInfo = conversation.getTargetInfo();
        if (targetInfo instanceof UserInfo) {
            UserInfo userInfo = (UserInfo) targetInfo;
            displayName = userInfo.getDisplayName();
            userName = userInfo.getUserName();
            if (latestMessage != null && latestMessage.getContent() instanceof TextContent) {
                lastContent =  ((TextContent) latestMessage.getContent()).getText();
            }
            setType(TYPE_SINGLE);
            setAvatarFile(userInfo.getAvatarFile());
        } else if (targetInfo instanceof GroupInfo) {
            GroupInfo groupInfo = (GroupInfo) targetInfo;
            displayName = groupInfo.getGroupName();
            setType(TYPE_GROUP);
            if (latestMessage != null && latestMessage.getContent() instanceof TextContent) {
                String name = latestMessage.getFromUser().getDisplayName();
                String content = ((TextContent) latestMessage.getContent()).getText();
                lastContent = name + ": " + content;
            }
            setAvatarFile(groupInfo.getAvatarFile());
        }
        unReadNum = unReadMsgCnt;
        lastTime = latestMessage == null ? "" : Util.getTimeForShow(latestMessage.getCreateTime());
        redVisible = unReadMsgCnt > 0;
    }


    /**
     * 点击最近聊天消息 item 的事件处理
     *
     * @param view
     */
    public void onConversionItemClick(View view) {
        // 当点击进入聊天时，未读消息置为0
        mConversion.setUnReadMessageCnt(0);
        setRedVisible(false);
        setUnReadNum(0);
        Intent intent = new Intent(mContext, PublicActivity.class);
        intent.putExtra("user_name", userName);
        intent.putExtra("display_name", displayName);
        if (TextUtils.isEmpty(userName)) {
            // 如果用户名为空的时候，就跳转到群组界面
            PublicActivity.startActivity((Activity) mContext, "com.weapon.joker.app.message.group.GroupFragment", intent);
        } else {
            // 如果用户名不为空的时候，就跳转到单聊界面
            PublicActivity.startActivity((Activity) mContext, "com.weapon.joker.app.message.single.SingleFragment", intent);
        }
    }


    /**
     * 长按最近消息列表事件处理
     *
     * @param view
     * @return
     */
    public boolean onConversionItemLongClick(View view) {
        Button button = new Button(view.getContext());
        button.setText("删除此会话");
        final CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(view.getContext()).setView(button)
                                                                                                   .setFocusable(true)
                                                                                                   .setOutsideTouchable(true)
                                                                                                   .create();
        popWindow.showAsDropDown(view, view.getHeight(), -view.getHeight() / 2, Gravity.TOP);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWindow != null && popWindow.getPopupWindow().isShowing()) {
                    popWindow.dissmiss();
                }
                deleteConversion();
            }
        });
        return true;
    }

    /**
     * 删除当前会话
     */
    private void deleteConversion() {
        Object targetInfo = mConversion.getTargetInfo();
        if (targetInfo instanceof UserInfo) {
            UserInfo userInfo = (UserInfo) targetInfo;
            JMessageClient.deleteSingleConversation(userInfo.getUserName());
        } else if (targetInfo instanceof GroupInfo) {
            GroupInfo groupInfo = (GroupInfo) targetInfo;
            JMessageClient.deleteGroupConversation(groupInfo.getGroupID());
        }
        if (mListener != null) {
            mListener.onDeleteSuccess();
        }
        Toast.makeText(mContext, "删除「" + displayName + "」会话成功!", Toast.LENGTH_SHORT).show();
    }

    private OnDeleteConversionListener mListener;

    /**
     * 删除当前会话成功后的回调接口
     */
    public interface OnDeleteConversionListener {
        void onDeleteSuccess();
    }

}
