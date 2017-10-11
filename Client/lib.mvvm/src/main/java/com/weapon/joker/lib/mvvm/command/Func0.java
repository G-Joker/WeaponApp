package com.weapon.joker.lib.mvvm.command;

import java.util.concurrent.Callable;

/**
 * Created by foxleezh on 2017/9/1.
 */

public interface Func0<R> extends Callable<R> {
    @Override
    R call();
}
