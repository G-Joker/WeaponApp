package com.weapon.joker.app.message.single;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;
import com.weapon.joker.app.message.databinding.FragmentSingleBinding;
import com.weapon.joker.lib.middleware.PublicActivity;
import com.weapon.joker.lib.middleware.utils.LogUtils;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.single.SingleFragment
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/18
 *     desc   : 官方消息界面
 * </pre>
 */

public class SingleFragment extends BaseFragment<SingleViewModel, SingleModel> implements SingleContact.View {

    /**
     * WeaponApp 官方客服账号
     */
    private static final String OFFICE_SERVICE = "WeaponApp";
    /**
     * 当前正在聊天的 username
     */
    private String mUserName = OFFICE_SERVICE;
    private String mDisplayName = "小之";

    private FragmentSingleBinding mDataBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_single;
    }

    @Override
    public void initView() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 注册消息接收事件
        JMessageClient.registerEventReceiver(this);
        mDataBinding = ((FragmentSingleBinding) getViewDataBinding());
        if (getActivity().getIntent() != null) {
            Intent intent = getActivity().getIntent();
            if (!TextUtils.isEmpty(intent.getStringExtra("user_name"))) {
                mUserName = intent.getStringExtra("user_name");
                LogUtils.i("office", "当前聊天的 name:" + mUserName);
            }
            if (!TextUtils.isEmpty(intent.getStringExtra("display_name"))) {
                mDisplayName = intent.getStringExtra("display_name");
            }
        }
        setToolbar();
        getViewModel().init(mUserName);
        mDataBinding.recyclerView.setItemAnimator(null);
    }

    /**
     * Toolbar 相关设置
     */
    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mDataBinding.toolbar);
        // 设置 toolbar 具有返回按钮
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        mDataBinding.toolbar.setTitle(mDisplayName);
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

    @Override
    public int getBR() {
        return BR.singleModel;
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
    public void onResume() {
        super.onResume();
        if (JMessageClient.getMyInfo() == null) {
            finish();
        }
    }

    @Override
    public void onDestroy() {
        // 取消消息接收事件的注册
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
        Message message = event.getMessage();
        switch (message.getContentType()) {
            case text:
                // 处理文字消息
                String userName = ((UserInfo) message.getTargetInfo()).getUserName();
                if (TextUtils.equals(userName, mUserName)) {
                    // 当收到的消息是官方消息才进行更新UI
                    getViewModel().receiveMessage(message);
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
        Message message = event.getMessage();
        String userName = ((UserInfo) message.getTargetInfo()).getUserName();
        String displayName = ((UserInfo) message.getTargetInfo()).getDisplayName();
        if (!TextUtils.equals(userName, mUserName)) {
            // 如果不是官方消息，才会跳转到跟别的用户聊天
            Intent intent = new Intent(getContext(), PublicActivity.class);
            intent.putExtra("user_name", userName);
            intent.putExtra("display_name", displayName);
            PublicActivity.startActivity(getActivity(), "com.weapon.joker.app.message.single.SingleFragment", intent);

        }
    }
}
