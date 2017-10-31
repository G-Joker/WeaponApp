package com.weapon.joker.app.message.friend;

import com.weapon.joker.lib.mvvm.common.BaseModel;
import com.weapon.joker.lib.mvvm.common.BaseView;
import com.weapon.joker.lib.mvvm.common.BaseViewModel;

import cn.jpush.im.android.api.model.UserInfo;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.friend.SearchFriendContact
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/31
 *     desc   :
 * </pre>
 */

public interface SearchFriendContact {
    interface View extends BaseView {

    }

    abstract class ViewModel extends BaseViewModel<View, Model> {
        abstract void searchSuccess(UserInfo userInfo);

        abstract void searchFailed(String desc);
    }

    abstract class Model extends BaseModel<ViewModel> {
        abstract void doSearchFriend(String userName);
    }
}
