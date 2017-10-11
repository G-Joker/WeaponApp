package com.weapon.joker.lib.mvvm.common;

import android.databinding.BindingAdapter;

import com.weapon.joker.lib.mvvm.command.ReplyCommand;
import com.weapon.joker.lib.mvvm.pullrefreshload.PullToRefreshLayout;

/**
 * ViewBindingAdapter 放置一些公用的 @BindingAdapter
 * 配合 Command 使用
 * author:张冠之
 * time: 2017/9/8 下午7:00
 * e-mail: guanzhi.zhang@sojex.cn
 */

public final class ViewBindingAdapter {


    @BindingAdapter (value = {"onRefreshCommand", "onLoadCommand"}, requireAll = false)
    public static void onRefreshCommand(
            final PullToRefreshLayout pullToRefreshLayout, final ReplyCommand onRefreshCommand, final ReplyCommand onLoadCommand) {

        pullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                if (onRefreshCommand != null) {
                    onRefreshCommand.execute();
                }
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                if (onLoadCommand != null) {
                    onLoadCommand.execute();
                }
            }
        });

    }
}
