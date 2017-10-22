package com.weapon.joker.lib.middleware.view.pullrefreshload;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.mine.pulltorefresh.PullToRefreshRecyclerView
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/10
 *     desc   : 配合着 {@link PullToRefreshLayout} 实现下拉刷新和上拉加载更多功能
 * </pre>
 */

public class PullToRefreshRecyclerView extends RecyclerView implements Pullable {

    /**
     * 是否可以上拉加载
     */
    private boolean isCanLoadMore = true;
    /**
     * 是否可以下拉刷新
     */
    private boolean isCanRefresh  = true;

    public PullToRefreshRecyclerView(Context context) {
        this(context, null);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);
    }

    private void initView(Context context, AttributeSet attrs, int defStyle) {

    }

    @Override
    public boolean canPullDown() {
        LayoutManager layoutManager = getLayoutManager();

        if (layoutManager.getItemCount() == 0) {
            return isCanRefresh;
        } else if (layoutManager instanceof LinearLayoutManager) {
            int firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
            if (firstVisibleItemPosition <= 0) {
                return isCanRefresh;
            } else {
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean canPullUp() {
        LayoutManager layoutManager = getLayoutManager();

        if (layoutManager.getItemCount() == 0) {
            return isCanLoadMore;
        } else if (layoutManager instanceof LinearLayoutManager) {
            int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition >= (layoutManager.getItemCount() - 1)) {
                return isCanLoadMore;
            } else {
                return false;
            }
        }

        return false;
    }

    public void setCanLoadMore(boolean canLoadMore) {
        isCanLoadMore = canLoadMore;
    }

    public void setCanRefresh(boolean canRefresh) {
        isCanRefresh = canRefresh;
    }

}
