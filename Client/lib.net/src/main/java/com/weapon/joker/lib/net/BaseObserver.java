package com.weapon.joker.lib.net;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.weapon.joker.lib.middleware.utils.LogUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * BaseObserver Observer 基类
 * author:张冠之
 * time: 2017/10/11 下午6:05
 * e-mail: guanzhi.zhang@sojex.cn
 */

public abstract class BaseObserver<T> implements Observer<T> {

    private Context ctx;
    /** dialog 上显示的文字 */
    private String message;

    public BaseObserver(Context ctx, String message){
        this.ctx = ctx;
        this.message = message;
    }

    public BaseObserver(Context ctx) {
        this(ctx,"请稍候");
    }

    public BaseObserver() {
        this(null,"请稍候");
    }



    @Override
    public void onSubscribe(Disposable d) {
        //dialog 开启
    }

    @Override
    public void onComplete() {
        //dialog 关闭
    }

    @Override
    public void onNext(@NonNull T tBaseEntry) {
        try {
            onSuccess(tBaseEntry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        LogUtils.logw("onError", e.toString());
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    /**
     * 返回成功
     */
    protected abstract void onSuccess(T entry) throws Exception;

    /**
     * 返回失败
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

}
