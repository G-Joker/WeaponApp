package com.weapon.joker.app.message.group;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;
import com.weapon.joker.app.message.databinding.FragmentGroupBinding;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;

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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        JMessageClient.registerEventReceiver(this);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_office_clear, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        new AlertDialog.Builder(getActivity()).setTitle("提示")
                                              .setMessage("确认清空聊天记录？")
                                              .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                                  @Override
                                                  public void onClick(DialogInterface dialog, int which) {
                                                      getViewModel().deleteAllMessage();
                                                  }
                                              })
                                              .setNegativeButton("取消", null)
                                              .show();
        return true;
    }

    @Override
    public void refreshFinish(int refreshResult) {
        mDataBinding.pullRefreshLayout.refreshFinish(refreshResult);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (JMessageClient.getMyInfo() == null) {
            finish();
        }
    }

    @Override
    public void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    /**
     * 接收消息事件
     * 目前只支持文字消息，后面再进行优化
     *
     * @param event 消息事件
     */
    public void onEventMainThread(MessageEvent event) {
        getViewModel().receiveMessage(event);
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
