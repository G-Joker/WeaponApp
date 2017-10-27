package com.weapon.joker.app.message.single;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.File;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.office.MessageItemViewModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/18
 *     desc   : 消息 viewModel
 * </pre>
 */

public class MessageItemViewModel extends BaseObservable{

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
}
