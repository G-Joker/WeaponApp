package com.weapon.joker.app.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * HomeFragment 首页 Fragment
 * author:张冠之
 * time: 2017/9/10 下午2:17
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class MainFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_home,container,false);
        return rootView;
    }
}
