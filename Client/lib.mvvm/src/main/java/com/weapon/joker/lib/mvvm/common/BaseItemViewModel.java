package com.weapon.joker.lib.mvvm.common;

/**
 * BaseItemViewModel adapter 类控件 Item VM 基类
 * 使用的时候，需要绑定的属性通过带有 @Bindable 注解的 get方法生成 BR 文件
 * 并设置相应的 set 方法
 * 子类继承需要重写构造方法，保留 super(M model)，并在构造方法内进行初始化 set 设置
 * author:张冠之
 * time: 2017/9/8 下午10:33
 * e-mail: guanzhi.zhang@sojex.cn
 */

public abstract class BaseItemViewModel<M extends BaseEntry> extends BaseViewModel<Void,M> {
    private M model;

    protected BaseItemViewModel(M model){
        this.model = model;
    }
}
