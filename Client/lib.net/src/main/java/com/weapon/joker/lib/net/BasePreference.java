package com.weapon.joker.lib.net;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.net.BasePreference
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/13
 *     desc   : 缓存基类
 * </pre>
 */

public class BasePreference {

    private Context mContext;
    protected SharedPreferences mPreferences;
    protected SharedPreferences.Editor mEditor;

    public BasePreference(Context context) {
        this.mContext = context;
        mPreferences = context.getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }
}
