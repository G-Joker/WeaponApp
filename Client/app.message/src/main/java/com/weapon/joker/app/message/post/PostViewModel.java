package com.weapon.joker.app.message.post;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.widget.Toast;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;
import com.weapon.joker.lib.middleware.utils.PreferencesUtils;
import com.weapon.joker.lib.mvvm.command.Action0;
import com.weapon.joker.lib.mvvm.command.ReplyCommand;
import com.weapon.joker.lib.mvvm.pullrefreshload.PullToRefreshLayout;
import com.weapon.joker.lib.net.GsonUtil;
import com.weapon.joker.lib.net.bean.PushNewsBean;
import com.weapon.joker.lib.net.model.PushNewsModel;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.post.PostViewModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/17
 *     desc   :
 * </pre>
 */

public class PostViewModel extends PostContact.ViewModel {

    /**
     * 是否可以上拉加载更多
     */
    @Bindable
    public boolean canLoadMore = false;
    /**
     * 是否可以下拉刷新
     */
    @Bindable
    public boolean canRefresh  = true;

    /**
     * 获取公告数据
     */
    public void getPostData(boolean isRefresh) {
        items.clear();
        if (isRefresh) {
            Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
            getView().refreshFinish(PullToRefreshLayout.SUCCEED);
        }
        //模拟数据
//        for (int i = 0; i < 20; i++) {
//            PostItemViewModel model = new PostItemViewModel();
//            model.title = "title:\t" + i;
//            model.content = "content:\t" + i;
//            items.add(model);
//        }

        String        pushNews = PreferencesUtils.getString(getContext(), "push_news", "");
        PushNewsModel model    = GsonUtil.getInstance().fromJson(pushNews, PushNewsModel.class);
        for (PushNewsBean bean : model.data) {
            PostItemViewModel itemViewModel = new PostItemViewModel();
            itemViewModel.content = bean.content;
            itemViewModel.title = bean.title;
            itemViewModel.url = bean.url;
            items.add(itemViewModel);
        }
    }


    public final ReplyCommand onRefreshCommand = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            getPostData(true);
        }
    });

    public final ItemBinding<PostItemViewModel>                        singleItem = ItemBinding.of(BR.postItemModel,
                                                                                                   R.layout.item_message_post);
    public final ObservableList<PostItemViewModel>                     items      = new ObservableArrayList<>();
    public final BindingRecyclerViewAdapter.ItemIds<PostItemViewModel> itemIds
                                                                                  = new BindingRecyclerViewAdapter.ItemIds<PostItemViewModel>() {
        @Override
        public long getItemId(int position, PostItemViewModel item) {
            return position;
        }
    };
}
