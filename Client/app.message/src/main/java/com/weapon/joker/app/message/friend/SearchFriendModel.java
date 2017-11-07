package com.weapon.joker.app.message.friend;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.friend.SearchFriendModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/31
 *     desc   :
 * </pre>
 */

public class SearchFriendModel extends SearchFriendContact.Model {

    @Override
    void doSearchFriend(String userName) {
        JMessageClient.getUserInfo(userName, new GetUserInfoCallback() {
            @Override
            public void gotResult(int status, String desc, UserInfo userInfo) {
                if (status == 0) {
                    getViewModel().searchSuccess(userInfo);
                } else {
                    getViewModel().searchFailed(desc);
                }
            }
        });
    }

}
