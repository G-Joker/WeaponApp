package com.weapon.joker.app.message.group;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;
import com.weapon.joker.app.message.databinding.FragmentGroupBinding;
import com.weapon.joker.lib.middleware.utils.LogUtils;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;

/**
 * class：   com.weapon.joker.app.message.group.GroupFragment
 * author：  xiaweizi
 * date：    2017/10/22 11:58
 * e-mail:   1012126908@qq.com
 * desc:
 */
public class GroupFragment extends BaseFragment<GroupViewModel, GroupModel> implements GroupContact.View {

    /**
     * 默认群 ID
     */
    private static final long GROUP_ID = 23349803;
    private FragmentGroupBinding mDataBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_group;
    }

    @Override
    public void initView() {
        mDataBinding = ((FragmentGroupBinding) getViewDataBinding());
        setToolbar();
        getViewModel().init();
    }

    @Override
    public int getBR() {
        return BR.groupModel;
    }

    /**
     * Toolbar 相关设置
     */
    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mDataBinding.toolbar);
        // 设置 toolbar 具有返回按钮
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        mDataBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void scrollToPosition(int position) {
        mDataBinding.recyclerView.scrollToPosition(position);
        mDataBinding.executePendingBindings();
    }

    @Override
    public void refreshFinish(int refreshResult) {
        mDataBinding.pullRefreshLayout.refreshFinish(refreshResult);
    }

    /**
     * 接收消息事件
     * 目前只支持文字消息，后面再进行优化
     *
     * @param event 消息事件
     */
    public void onEventMainThread(MessageEvent event) {
        Message message = event.getMessage();
        switch (message.getContentType()) {
            case text:
                // 处理文字消息
                TextContent textContent = (TextContent) message.getContent();
                String text = textContent.getText();
                Object targetInfo = message.getTargetInfo();
                if (targetInfo instanceof GroupInfo) {
                    GroupInfo groupInfo = (GroupInfo) targetInfo;
                    if (groupInfo.getGroupID() == GROUP_ID) {
                        getViewModel().receiveMessage(text);
                    }
                }
            default:
                LogUtils.i("office", message.getFromType());
                break;
        }
    }

    /**
     * 点击通知消息的事件处理
     * @param event 收到的点击通知消息事件
     */
    public void onEventMainThread(NotificationClickEvent event) {
        if (event == null) {
            return;
        }
    }
}
