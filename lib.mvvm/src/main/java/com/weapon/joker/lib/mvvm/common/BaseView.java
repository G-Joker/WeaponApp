package com.weapon.joker.lib.mvvm.common;

/**
 * Created by pc on 2017/9/2.
 */

public interface BaseView {
    /*********************子类实现***********************/
    //获取布局文件
    int getLayoutId();

    //页面初始化操作
    void initView();

    //获取 BR 参数
    int getBR();

    //页面的 finish 操作
    void finish();

}
