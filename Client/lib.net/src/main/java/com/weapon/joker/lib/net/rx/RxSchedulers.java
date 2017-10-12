package com.weapon.joker.lib.net.rx;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * RxSchedulers RxJava统一线程管理
 * author:张冠之
 * time: 2017/10/12 下午2:13
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class RxSchedulers {
    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }

        };
    }
}
