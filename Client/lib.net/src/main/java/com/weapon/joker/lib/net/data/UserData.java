package com.weapon.joker.lib.net.data;

import android.content.Context;
import android.text.TextUtils;

import com.weapon.joker.lib.middleware.utils.LogUtils;
import com.weapon.joker.lib.middleware.utils.PreferencesUtils;
import com.weapon.joker.lib.net.GsonUtil;
import com.weapon.joker.lib.net.bean.CommonBean.UserBean;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.net.data.UserData
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/13
 *     desc   :
 * </pre>
 */

public class UserData{

    private static UserData instance;
    private static final String TAG = UserData.class.getSimpleName() + "_";

    private static final String USER_DATA = "user_data";

    public static UserData getInstance() {
        return instance == null ? (instance = new UserData()) : instance;
    }

    /**
     * 保存用户信息到缓存
     */
    public void setUserBean(Context context,UserBean userData) {
        LogUtils.d(TAG, "setUserBean : " + GsonUtil.getInstance().toJson(userData));
        PreferencesUtils.putString(context,USER_DATA,GsonUtil.getInstance().toJson(userData));
    }

    /**
     * @return 获取缓存的用户信息
     */
    public UserBean getUserBean(Context context) {
        String userData = PreferencesUtils.getString(context,USER_DATA,"");
        LogUtils.d(TAG, "getUserBean : " + userData);
        if (TextUtils.isEmpty(userData)) {
            return new UserBean();
        }
        return GsonUtil.getInstance().fromJson(userData, UserBean.class);
    }

    /**
     * 清除用户缓存数据
     */
    public void clearUserData(Context context) {
        PreferencesUtils.remove(context, USER_DATA);
    }
}