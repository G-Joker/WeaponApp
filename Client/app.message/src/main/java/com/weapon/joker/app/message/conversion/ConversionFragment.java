package com.weapon.joker.app.message.conversion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;
import com.weapon.joker.app.message.databinding.FragmentConversionBinding;
import com.weapon.joker.lib.mvvm.common.BaseFragment;
import com.weapon.joker.lib.mvvm.common.PublicActivity;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.conversion.ConversionFragment
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/20
 *     desc   :
 * </pre>
 */

public class ConversionFragment extends BaseFragment<ConversionViewModel, ConversionModel> implements ConversionContact.View {

    private FragmentConversionBinding mDataBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_conversion;
    }

    @Override
    public void initView() {
        JMessageClient.registerEventReceiver(this);
        mDataBinding = ((FragmentConversionBinding) getViewDataBinding());
        setToolbar();
        getViewModel().init();
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
    public int getBR() {
        return BR.conversionModel;
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
        if (event == null) {
            return;
        }
        getViewModel().init();
    }

    /**
     * 点击通知消息的事件处理
     * @param event 收到的点击通知消息事件
     */
    public void onEvent(NotificationClickEvent event) {
        if (event == null) {
            return;
        }
        String userName = event.getMessage().getFromUser().getUserName();
        Intent intent = new Intent(getContext(), PublicActivity.class);
        intent.putExtra("user_name", userName);
        PublicActivity.startActivity(getActivity(), "com.weapon.joker.app.message.office.OfficeFragment", intent);
    }
}
