package com.weapon.joker.app.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * MessageFragment 消息 Fragment
 * author:张冠之
 * time: 2017/9/10 下午2:28
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class MessageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_message,container,false);
        return root;
    }
}
