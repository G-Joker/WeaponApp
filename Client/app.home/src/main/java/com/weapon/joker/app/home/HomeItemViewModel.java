package com.weapon.joker.app.home;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.weapon.joker.lib.middleware.utils.Util;
import com.weapon.joker.lib.net.bean.HomeBean.RecommandBodyValue;

import java.util.ArrayList;

/**
 * HomeItemViewModel 首页ItemViewModel
 * author:张冠之
 * time: 2018/2/22 下午2:10
 * e-mail: guanzhi.zhang@sojex.cn
 */
public class HomeItemViewModel extends BaseObservable {
    public int type = CARD_TYPE_ONE;
    public static final int VIDEO_TYPE = 0x00;
    public static final int CARD_TYPE_ONE = 0x01;
    public static final int CARD_TYPE_TWO = 0x02;
    public static final int CARD_TYPE_THREE = 0x03;

    @Bindable
    public RecommandBodyValue bean;


    public HomeItemViewModel(RecommandBodyValue bean){
        this.type = bean.type;
        this.bean = bean;
    }

    @BindingAdapter("addImageViews")
    public static void setOnOffsetChanged(LinearLayout layout, ArrayList<String> list) {
        Context context = layout.getContext();
        layout.removeAllViews();
        for (String url:list) {
            ImageView photoView = new ImageView(layout.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.
                    LayoutParams(Util.dip2px(context, 100),
                    LinearLayout.LayoutParams.MATCH_PARENT);
            params.leftMargin = Util.dip2px(context, 5);
            photoView.setLayoutParams(params);
            Glide.with(photoView.getContext())
                    .load(url)
                    .into(photoView);
            layout.addView(photoView);
        }
    }

}
