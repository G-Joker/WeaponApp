package com.weapon.joker.app.message.single;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.view.View;

import com.weapon.joker.lib.middleware.PublicActivity;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.office.MessageItemViewModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/18
 *     desc   : 消息 viewModel
 * </pre>
 */

public class MessageItemViewModel extends BaseObservable {

    /**
     * 消息类型: 发送
     */
    public static final int MSG_SEND = 101;
    /**
     * 消息类型: 接收
     */
    public static final int MSG_RECEIVER = 102;
    /**
     * 消息类型: 时间
     */
    public static final int MSG_DATA = 103;

    /**
     * 消息的类型 (默认为发送类型)
     */
    public int type = MSG_DATA;
    /**
     * 消息内容
     */
    @Bindable
    public String content;
    /**
     * 展示的名称
     */
    @Bindable
    public String displayName;
    /**
     * 消息的时间
     */
    @Bindable
    public String msgData;
    /**
     * 头像地址
     */
    @Bindable
    public File avatarFile;
    /**
     * 用户名
     */
    @Bindable
    public String userName;


    public MessageItemViewModel(String msgData) {
        this(null, MessageItemViewModel.MSG_DATA, msgData);
    }

    public MessageItemViewModel(Message message, int type) {
        this(message, type, "");
    }

    public MessageItemViewModel(Message message, int type, String msgData) {
        this.type = type;
        this.msgData = msgData;
        if (message != null) {
            switch (message.getContentType()) {
                case text:
                    this.content = ((TextContent) message.getContent()).getText();
                    break;
                case image:
                    ImageContent imageContent = (ImageContent) message.getContent();
                    break;
                default:
                    break;
            }
            UserInfo fromUser = message.getFromUser();
            this.avatarFile = fromUser.getAvatarFile();
            this.userName = fromUser.getUserName();
            this.displayName = fromUser.getDisplayName();
        }
    }


    /**
     * 头像点击事件处理
     *
     * @param view
     */
    public void onAvatarClick(View view) {
        // 如果点击的是自己的头像，则跳转到个人中心界面
        if (TextUtils.equals(userName, JMessageClient.getMyInfo().getUserName())) {
            PublicActivity.startActivity((Activity) view.getContext(), "com.weapon.joker.app.mine.person.PersonCenterFragment");
        } else {
            Intent intent = new Intent(view.getContext(), PublicActivity.class);
            intent.putExtra("user_name", userName);
            PublicActivity.startActivity((Activity) view.getContext(), "com.weapon.joker.app.message.homepage.HomePageFragment", intent);
        }
    }
}
