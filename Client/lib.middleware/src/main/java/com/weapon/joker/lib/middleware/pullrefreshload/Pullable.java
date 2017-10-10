package com.weapon.joker.lib.middleware.pullrefreshload;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.mine.pulltorefresh.Pullable
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/10
 *     desc   : 同一判断是否可以下拉和上拉的接口
 *
 *     对于 {@link PullToRefreshLayout} 的子 view，需要实现 {@link Pullable} 的接口，这样所有的 View 都
 *     具有下拉刷新和上拉加载更多的功能
 * </pre>
 */

public interface Pullable {
    /**
     * 判断是否可以下拉，如果不需要下拉功能可以直接return false
     *
     * @return true如果可以下拉否则返回false
     */
    boolean canPullDown();

    /**
     * 判断是否可以上拉，如果不需要上拉功能可以直接return false
     *
     * @return true如果可以上拉否则返回false
     */
    boolean canPullUp();
}
