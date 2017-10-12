package com.weapon.joker.lib.mvvm.common;

import android.databinding.BaseObservable;

/**
 * BaseEntry 实体类基类
 * author:张冠之
 * time: 2017/9/8 下午10:24
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class BaseEntry<T> extends BaseObservable{
    public T data;
}
