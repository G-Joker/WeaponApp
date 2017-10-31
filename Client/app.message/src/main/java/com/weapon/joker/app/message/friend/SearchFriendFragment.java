package com.weapon.joker.app.message.friend;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.friend.SearchFriendFragment
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/31
 *     desc   :
 * </pre>
 */

public class SearchFriendFragment extends BaseFragment<SearchFriendViewModel, SearchFriendModel> implements SearchFriendContact.View {


    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_friend;
    }

    @Override
    public void initView() {

    }

    @Override
    public int getBR() {
        return BR.searchFriendModel;
    }
}
