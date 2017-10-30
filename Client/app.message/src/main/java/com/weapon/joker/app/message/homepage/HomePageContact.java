package com.weapon.joker.app.message.homepage;

import com.weapon.joker.lib.mvvm.common.BaseModel;
import com.weapon.joker.lib.mvvm.common.BaseView;
import com.weapon.joker.lib.mvvm.common.BaseViewModel;

import cn.jpush.im.android.api.model.UserInfo;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.homepage.HomePageContact
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/27
 *     desc   :
 * </pre>
 */

public interface HomePageContact {
    interface View extends BaseView{

    }

    abstract class ViewModel extends BaseViewModel<View, Model> {
        /**
         * 获取用户信息成功，设置用户信息
         * @param userInfo
         */
        abstract void setUserInfo(UserInfo userInfo);

        /**
         * 获取用户信息失败
         * @param desc 失败描述
         */
        abstract void setError(String desc);
    }

    abstract class Model extends BaseModel<ViewModel>{
        /**
         * 根据用户名获取用户信息
         * @param userName
         */
        abstract void getUserInfo(String userName);
    }
}
