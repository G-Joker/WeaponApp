package com.weapon.joker.app.mine.person;

import android.content.DialogInterface;
import android.databinding.Bindable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.weapon.joker.app.mine.BR;
import com.weapon.joker.lib.net.data.UserData;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.mine.person.PersonCenterViewModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/13
 *     desc   : 个人中心 ViewModel
 * </pre>
 */

public class PersonCenterViewModel extends PersonCenterContact.ViewModel {

    @Bindable
    public String userName;

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    /**
     * 设置个人中心头部数据
     */
    public void setHeaderData() {
        String userName = UserData.getInstance().getUserBean(getContext().getApplicationContext()).user;
        if (!TextUtils.isEmpty(userName)) {
            setUserName(userName);
        }
    }

    /**
     * 注销登录点击处理
     *
     * @param view
     */
    public void onLogoutClick(View view) {
        new AlertDialog.Builder(getContext())
                .setTitle("提示")
                .setMessage("确定退出登录？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 清楚本地数据
                        UserData.getInstance().clearUserData(getContext());
                        getView().finish();
                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }

}
