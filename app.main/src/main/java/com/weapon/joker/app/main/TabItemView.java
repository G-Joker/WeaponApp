package com.weapon.joker.app.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * TabItemView TabLayout 自定义 TabView
 * author:张冠之
 * time: 2017/9/10 上午11:43
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class TabItemView extends LinearLayout {
    private TextView mTextView;
    private ImageView mImageView;

    public TabItemView(Context context) {
        this(context, null);
    }

    public TabItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.layout_tab_item, this);
        mTextView = (TextView) root.findViewById(R.id.tv_tab);
        mImageView = (ImageView) root.findViewById(R.id.img_tab);

    }

    public TabItemView setText(String text) {
        mTextView.setText(text);
        return this;
    }

    public TabItemView setImageRes(int imgRes) {
        mImageView.setImageResource(imgRes);
        return this;
    }

}
