package com.weapon.joker.app.message;

import android.app.Activity;
import android.databinding.Bindable;
import android.view.View;
import android.widget.Toast;

import com.weapon.joker.lib.middleware.PublicActivity;
import com.weapon.joker.lib.middleware.utils.Util;
import com.weapon.joker.lib.net.data.PushNewsData;
import com.weapon.joker.lib.net.model.PushNewsModel;

/**
 * MessageViewModel 消息的VM
 * author:张冠之
 * time: 2017/10/12 下午1:45
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class MessageViewModel extends MessageContact.ViewModel {

    /**
     * 公告通知数量
     */
    @Bindable
    public int postNum;
    /**
     * 官方消息数量
     */
    @Bindable
    public int officeNum = 0;
    /**
     * 公告红点是否可见
     */
    @Bindable
    public int postRedVisible;
    /**
     * 官方服务红点是否可见
     */
    @Bindable
    public int serviceRedVisible = View.GONE;
    /**
     * 公告内容
     */
    @Bindable
    public String postContent;
    /**
     * 官方消息内容
     */
    @Bindable
    public String serviceContent = "暂无最新消息";

    public void setPostNum(int postNum) {
        this.postNum = postNum;
        notifyPropertyChanged(com.weapon.joker.app.message.BR.postNum);
    }

    public void setOfficeNum(int officeNum) {
        this.officeNum = officeNum;
        notifyPropertyChanged(com.weapon.joker.app.message.BR.officeNum);
    }

    public void setPostRedVisible(int postRedVisible) {
        this.postRedVisible = postRedVisible;
        notifyPropertyChanged(com.weapon.joker.app.message.BR.postRedVisible);
    }

    public void setServiceRedVisible(int serviceRedVisible) {
        this.serviceRedVisible = serviceRedVisible;
        notifyPropertyChanged(com.weapon.joker.app.message.BR.serviceRedVisible);
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
        notifyPropertyChanged(com.weapon.joker.app.message.BR.postContent);
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
        notifyPropertyChanged(com.weapon.joker.app.message.BR.serviceContent);
    }

    /**
     * 获取公告消息
     */
    @Override
    public void getPostNews() {
        PushNewsModel model = PushNewsData.getInstance().getPushNewsData(getContext().getApplicationContext());
        if (model == null || model.data == null || model.data.size() <= 0) {
            setPostRedVisible(View.GONE);
            setServiceRedVisible(View.GONE);
            setPostContent("暂无公告");
        } else {
            setPostNum(model.data.size());
            setPostRedVisible(View.VISIBLE);
            setPostContent(model.data.get(model.data.size() - 1).content);
        }
    }

    /**
     * 更新官方消息内容和数量
     *
     * @param content   内容
     * @param officeNum 数量
     */
    public void updateOfficeData(String content, int officeNum) {
        setServiceRedVisible(View.VISIBLE);
        setOfficeNum(this.officeNum += officeNum);
        setServiceContent(content);
    }

    /**
     * 公告点击事件处理
     *
     * @param view
     */
    public void onPostClick(View view) {
        if (postRedVisible == View.VISIBLE) {
            PublicActivity.startActivity((Activity) getContext(), "com.weapon.joker.app.message.post.PostFragment");
        } else {
            Toast.makeText(getContext(), "暂无公告", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 最新消息点击事件处理
     *
     * @param view
     */
    public void onServiceClick(View view) {
        // 重置官方消息提醒数量
        setOfficeNum(0);
        setServiceRedVisible(View.GONE);
        setServiceContent("暂无最新消息");
        // 如果未登录跳转到登录界面
        if (Util.checkHasLogin((Activity) getContext())) {
            PublicActivity.startActivity((Activity) getContext(), "com.weapon.joker.app.message.conversion.ConversionFragment");
        }
    }

    /**
     * 跳转到官方客服聊天界面
     *
     * @param view
     */
    public void onOfficeSingleClick(View view) {
        getView().toggleFloatingMenu();
        if (Util.checkHasLogin((Activity) getContext())) {
            PublicActivity.startActivity((Activity) getContext(), "com.weapon.joker.app.message.single.SingleFragment");
        }
    }

    /**
     * 跳转到官方群聊天界面
     *
     * @param view
     */
    public void onOfficeGroupClick(View view) {
        getView().toggleFloatingMenu();
        if (Util.checkHasLogin((Activity) getContext())) {
            PublicActivity.startActivity((Activity) getContext(), "com.weapon.joker.app.message.group.GroupFragment");
        }
    }

    /**
     * 跳转到添加聊天界面
     *
     * @param view
     */
    public void onOfficeSearchClick(View view) {
        getView().toggleFloatingMenu();
        if (Util.checkHasLogin((Activity) getContext())) {
            PublicActivity.startActivity((Activity) getContext(), "com.weapon.joker.app.message.friend.SearchFriendFragment");
        }
    }

}
