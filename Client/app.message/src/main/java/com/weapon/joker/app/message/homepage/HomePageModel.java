package com.weapon.joker.app.message.homepage;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.homepage.HomePageModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/27
 *     desc   :
 * </pre>
 */

public class HomePageModel extends HomePageContact.Model {

    /**
     * 根据用户名获取用户信息
     * @param userName 用户名
     */
    @Override
    public void getUserInfo(String userName) {
        JMessageClient.getUserInfo(userName, new GetUserInfoCallback() {
            @Override
            public void gotResult(int status, String desc, UserInfo userInfo) {
                if (status == 0) {
                    getViewModel().setUserInfo(userInfo);
                } else {
                    getViewModel().setError(desc);
                }
            }
        });
    }
}
