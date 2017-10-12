package com.weapon.joker.app.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weapon.joker.lib.middleware.utils.LogUtils;
import com.weapon.joker.lib.net.Api;
import com.weapon.joker.lib.net.BaseObserver;
import com.weapon.joker.lib.net.HostType;
import com.weapon.joker.lib.net.bean.MessageModel;

import io.reactivex.schedulers.Schedulers;

/**
 * MessageFragment 消息 Fragment
 * author:张冠之
 * time: 2017/9/10 下午2:28
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class MainFragment extends Fragment {
    private View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_message, container, false);
        request();
        return root;
    }


    private void request() {
        Api.getDefault(HostType.MESSAGE)
                .getCall()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(new BaseObserver<MessageModel>() {
                    @Override
                    protected void onSuccess(MessageModel entry) throws Exception {
                        entry.show();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        LogUtils.logi("http error");
                    }
                });


    }
}