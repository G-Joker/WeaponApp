package com.weapon.joker.app.mine.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weapon.joker.app.mine.R;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

/**
 * MineFragment 我的 Fragment
 * author:张冠之
 * time: 2017/9/10 下午2:43
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class MineFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mine, container, false);
        } else {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        }
        return view;
    }
}
