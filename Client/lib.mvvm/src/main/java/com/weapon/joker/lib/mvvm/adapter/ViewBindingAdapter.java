package com.weapon.joker.lib.mvvm.adapter;

import android.databinding.BindingAdapter;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.weapon.joker.lib.mvvm.R;
import com.weapon.joker.lib.mvvm.command.ReplyCommand;
import com.weapon.joker.lib.mvvm.transform.BlurTransformation;
import com.weapon.joker.lib.mvvm.transform.GlideCircleTransform;
import com.weapon.joker.lib.view.component.PublicForm;
import com.weapon.joker.lib.view.pullrefreshload.PullToRefreshLayout;
import com.weapon.joker.lib.view.pullrefreshload.PullToRefreshRecyclerView;

import java.io.File;

/**
 * ViewBindingAdapter 放置一些公用的 @BindingAdapter
 * 配合 Command 使用
 * author:张冠之
 * time: 2017/9/8 下午7:00
 * e-mail: guanzhi.zhang@sojex.cn
 */

public final class ViewBindingAdapter {


    /**
     * pullToRefreshLayout 上拉加载和下拉刷新的控制 adapter
     *
     * @param pullToRefreshLayout
     * @param onRefreshCommand
     * @param onLoadCommand
     */
    @BindingAdapter (value = {"onRefreshCommand", "onLoadCommand"}, requireAll = false)
    public static void onRefreshLoadCommand(
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

    @BindingAdapter ("onOffsetChanged")
    public static void setOnOffsetChanged(AppBarLayout appBarLayout, AppBarLayout.OnOffsetChangedListener listener) {
        if (listener != null) {
            appBarLayout.addOnOffsetChangedListener(listener);
        }
    }

    /**
     * 设置 view 是否可见
     */
    @BindingAdapter ("visibility")
    public static void setVisibility(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置 RecyclerView 是否可以下拉刷新和上拉加载更多
     *
     * @param recyclerView
     * @param canLoadMore
     * @param canRefresh
     */
    @BindingAdapter (value = {"canLoadMore", "canRefresh"}, requireAll = false)
    public static void setisLoadAndRefresh(PullToRefreshRecyclerView recyclerView, boolean canLoadMore, boolean canRefresh) {
        recyclerView.setCanLoadMore(canLoadMore);
        recyclerView.setCanRefresh(canRefresh);
    }

    /**
     * 给 ImageView 设置图片
     *
     * @param imageView 需要被设置的 ImageView
     * @param url       图片的 url
     * @param file      图片的文件
     * @param type      图片的类型
     *                  1 - 单聊默认头像
     *                  2 - 群聊默认头像
     *                  3 - 高斯模糊背景
     *                  4 - 其他
     */
    @BindingAdapter (value = {"url", "file", "type", "isRound"}, requireAll = false)
    public static void setImageViewUrl(ImageView imageView, String url, File file, int type,boolean isRound) {
        RequestOptions requestOptions = new RequestOptions();
        GlideCircleTransform glideCircleTransform = new GlideCircleTransform();
        if (isRound) {
            requestOptions.transform(glideCircleTransform).dontAnimate();
        }
        switch (type) {
            case 1:
                requestOptions.placeholder(R.drawable.ic_single_default);
                break;
            case 2:
                requestOptions.placeholder(R.drawable.ic_group_default);
                break;
            case 3:
                BlurTransformation blurTransformation = new BlurTransformation(imageView.getContext());
                requestOptions.transform(blurTransformation).placeholder(R.drawable.ic_group_default);
                break;
            default:
                requestOptions.placeholder(R.mipmap.ic_avatar_default);
                break;
        }
        Glide.with(imageView.getContext()).load(file).apply(requestOptions).into(imageView);
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext())
                 .load(url)
                 .transition(new DrawableTransitionOptions().crossFade())
                 .apply(requestOptions.placeholder(R.mipmap.round))
                 .into(imageView);
        }


    }

    @BindingAdapter (value = {"form_title", "form_content"}, requireAll = false)
    public static void setForm(PublicForm publicForm, String title, String content) {
        publicForm.setTitle(title);
        publicForm.setContent(content);
    }

    @BindingAdapter(value={"pageMargin","pageLimit"},requireAll = false)
    public static void setPageMargin(ViewPager viewPager,int margin,int pageLimit){
        if (pageLimit != 0){
            viewPager.setOffscreenPageLimit(pageLimit);
        }
        viewPager.setPageMargin(margin);
    }

    @BindingAdapter("currentItem")
    public static void setCurrentItem(final ViewPager viewPager,final int currentItem){
        new Handler().postDelayed(() -> viewPager.setCurrentItem(currentItem), 500);
    }

    @BindingAdapter("onScroll")
    public static void setOnScroll(ListView listView,final ReplyCommand replyCommand){
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (replyCommand!=null) {
                    replyCommand.execute();
                }
            }
        });
    }
}
