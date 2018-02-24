package com.weapon.joker.app.home.video;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.weapon.joker.lib.net.bean.HomeBean.RecommandBodyValue;
import com.weapon.joker.lib.video.activity.AdBrowserActivity;
import com.weapon.joker.lib.video.core.AdContextInterface;
import com.weapon.joker.lib.video.core.video.VideoAdContext;

/**
 * VideoLayout 视频播放Layout
 * author:张冠之
 * time: 2018/2/24 下午2:48
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class VideoLayout extends RelativeLayout {

    private VideoAdContext mAdContext;
    private RecommandBodyValue value;

    public VideoLayout(Context context) {
        this(context, null);
    }

    public VideoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setData(RecommandBodyValue value){
        this.value = value;
        mAdContext = new VideoAdContext(this,new Gson().toJson(value),null);
        mAdContext.setAdResultListener(new AdContextInterface() {
            @Override
            public void onAdSuccess() {

            }

            @Override
            public void onAdFailed() {

            }

            @Override
            public void onClickVideo(String url) {
                Intent intent = new Intent(getContext(), AdBrowserActivity.class);
                intent.putExtra(AdBrowserActivity.KEY_URL,url);
                getContext().startActivity(intent);
            }
        });
    }

    //自动播放方法
    public void updateAdInScrollView() {
        if (mAdContext != null) {
            mAdContext.updateAdInScrollView();
        }
    }

    @BindingAdapter("updateScrollView")
    public static void updateScroll(VideoLayout layout,int index){
        layout.updateAdInScrollView();
    }

    @BindingAdapter("setVideoData")
    public static void setVideoData(VideoLayout layout,RecommandBodyValue value){
        layout.setData(value);
    }



}
