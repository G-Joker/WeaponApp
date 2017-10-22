package com.weapon.joker.app.message.conversion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.lib.middleware.utils.Util;
import com.weapon.joker.lib.middleware.PublicActivity;

import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.conversion.ConversionItemViewModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/20
 *     desc   :
 * </pre>
 */

public class ConversionItemViewModel extends BaseObservable {

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

    public ConversionItemViewModel(Context context, Conversation conversation) {
        mContext = context;
        mConversion = conversation;
        // 获取未读消息数量
        int unReadMsgCnt = conversation.getUnReadMsgCnt();
        Message latestMessage = conversation.getLatestMessage();
        if (latestMessage == null) {
            return;
        }
        displayName = ((UserInfo) latestMessage.getTargetInfo()).getDisplayName();
        userName = ((UserInfo) latestMessage.getTargetInfo()).getUserName();
        lastContent = ((TextContent) latestMessage.getContent()).getText();
        unReadNum = unReadMsgCnt;
        lastTime = Util.getStrTime(latestMessage.getCreateTime(), "yyyy-MM-dd HH:mm");
        redVisible = unReadMsgCnt > 0;

    }


    public void onConversionItemClick(View view) {
        // 当点击进入聊天时，未读消息置为0
        mConversion.setUnReadMessageCnt(0);
        setRedVisible(false);
        setUnReadNum(0);
        Intent intent = new Intent(mContext, PublicActivity.class);
        intent.putExtra("user_name", userName);
        intent.putExtra("display_name", displayName);
        PublicActivity.startActivity((Activity) mContext, "com.weapon.joker.app.message.office.OfficeFragment", intent);
    }

}
