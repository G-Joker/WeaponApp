package com.weapon.joker.app.message.conversion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.weapon.joker.lib.mvvm.common.PublicActivity;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.conversion.ConversionItemViewModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/20
 *     desc   :
 * </pre>
 */

public class ConversionItemViewModel extends BaseObservable {

    /**
     * 显示的 name
     */
    @Bindable
    public String displayName;
    /**
     * 最新内容
     */
    @Bindable
    public String lastContent;

    /**
     * 最新消息时间
     */
    @Bindable
    public String lastTime;

    /**
     * 用户名
     */
    public String userName;
    /**
     * 未读消息数量
     */
    @Bindable
    public int unReadNum;

    private Context mContext;

    public ConversionItemViewModel(Context context) {
        mContext = context;
    }


    public void onConversionItemClick(View view) {
        Intent intent = new Intent(mContext, PublicActivity.class);
        intent.putExtra("user_name", userName);
        PublicActivity.startActivity((Activity) mContext, "com.weapon.joker.app.message.office.OfficeFragment", intent);
    }

}
