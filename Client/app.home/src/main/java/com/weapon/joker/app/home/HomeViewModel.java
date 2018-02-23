package com.weapon.joker.app.home;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.view.View;

import com.weapon.joker.app.home.header.HomeHeaderViewModel;
import com.weapon.joker.lib.middleware.utils.LogUtils;
import com.weapon.joker.lib.net.BaseObserver;
import com.weapon.joker.lib.net.bean.HomeBean.HomeBean;
import com.weapon.joker.lib.net.bean.HomeBean.RecommandBodyValue;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.BindingListViewAdapter;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;

/**
 * HomeViewModel 首页Fragment ViewModel
 * author:张冠之
 * time: 2018/2/22 上午10:47
 * e-mail: guanzhi.zhang@sojex.cn
 */
public class HomeViewModel extends HomeContact.ViewModel {

    @Bindable
    public boolean listViewVisibility = false;

    public void setListViewVisibility(boolean visibility){
        listViewVisibility = visibility;
        notifyPropertyChanged(BR.listViewVisibility);
    }

    @Override
    void requestRecommandData() {
        getModel().getHomeListData().subscribe(new BaseObserver<HomeBean>() {
            @Override
            protected void onSuccess(HomeBean entry) throws Exception {
                LogUtils.i("onSuccess"+entry.status);
                if (entry.data.list != null && entry.data.list.size()>0){
                    setListViewVisibility(true);
                    refreshListData(entry);
                }else {
                    setListViewVisibility(false);
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                LogUtils.i("onFailure",e.getMessage());
            }
        });
    }

    /** 刷新list数据 */
    private void refreshListData(HomeBean bean) {
        headerViewModel.setHeadValue(bean.data.head);
        List<HomeItemViewModel> temp = new ObservableArrayList<>();
        for (RecommandBodyValue value : bean.data.list) {
            HomeItemViewModel viewModel = new HomeItemViewModel(value);
            temp.add(viewModel);
        }
        items.clear();
        items.addAll(temp);
    }

    /** 二维码点击事 */
    public void onQrcodeClick(View view) {
        LogUtils.i("qrcode is clicked");
    }

    /** 菜单点击事件 */
    public void onCategoryClick(View view) {
        LogUtils.i("category is clicked");
    }

    /** 索引点击事件 */
    public void onSearchTextClick(View view) {
        LogUtils.i("search is clicked");
    }

    /** RecyclerView 相关 */
    public HomeHeaderViewModel headerViewModel = new HomeHeaderViewModel();//这里必须要先初始化
    public final ObservableList<HomeItemViewModel> items = new ObservableArrayList<>();
    //添加HeaderView
    public final MergeObservableList<Object> headerItems = new MergeObservableList<>()
            .insertItem(headerViewModel)
            .insertList(items);
    public final BindingListViewAdapter.ItemIds<Object> itemIds = (position, item) -> position;
    public final OnItemBind<Object> multiItems = (itemBinding, position, item) -> {
        if (HomeHeaderViewModel.class.equals(item.getClass())){
            itemBinding.set(BR.itemViewModel,R.layout.item_home_list_header);
        }else if (HomeItemViewModel.class.equals(item.getClass())){
            switch (((HomeItemViewModel)item).type) {
                case HomeItemViewModel.VIDEO_TYPE:
                    itemBinding.set(BR.itemViewModel, R.layout.item_home_video);
                    break;
                case HomeItemViewModel.CARD_TYPE_ONE:
                    itemBinding.set(BR.itemViewModel, R.layout.item_home_card_one);
                    break;
                case HomeItemViewModel.CARD_TYPE_TWO:
                    itemBinding.set(BR.itemViewModel, R.layout.item_home_card_two);
                    break;
                case HomeItemViewModel.CARD_TYPE_THREE:
                    itemBinding.set(BR.itemViewModel, R.layout.item_home_card_three);
                    break;
            }
        }
    };
}
