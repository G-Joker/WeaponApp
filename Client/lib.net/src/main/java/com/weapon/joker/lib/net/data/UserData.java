package com.weapon.joker.lib.net.data;

import android.content.Context;
import android.text.TextUtils;

import com.weapon.joker.lib.net.BasePreference;
import com.weapon.joker.lib.net.GsonUtil;
import com.weapon.joker.lib.net.bean.UserBean;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.net.data.UserData
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/13
 *     desc   :
 * </pre>
 */

public class UserData extends BasePreference {

    private static UserData instance;

    private static final String USER_DATA = "user_data";

    private UserData(Context context) {
        super(context);
    }

    public static UserData getInstance(Context context) {
        return instance == null ? (instance = new UserData(context)) : instance;
    }

    /**
     * 保存用户信息到缓存
     *
     * @param userData
     */
    public void setUserBean(UserBean userData) {
        mEditor.putString(USER_DATA, GsonUtil.getInstance().toJson(userData));
        mEditor.apply();
    }

    /**
     * @return 获取缓存的用户信息
     */
    public UserBean getUserBean() {
        String userData = mPreferences.getString(USER_DATA, "");
        if (TextUtils.isEmpty(userData)) {
            return new UserBean();
        }

        return GsonUtil.getInstance().fromJson(userData, UserBean.class);
    }

    /**
     * 清除用户缓存数据
     */
    public void clearUserData() {
        mEditor.clear();
        mEditor.apply();
    }
}
