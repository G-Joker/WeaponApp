package com.weapon.joker.lib.video.core.display;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.weapon.joker.lib.video.adutil.ImageLoaderUtil;
import com.weapon.joker.lib.video.core.BaseRenderer;


/**
 * Created by renzhiqiang on 16/10/27.
 *
 * @function 图片类型广告
 */

public class ImageAdRenderer extends BaseRenderer {

    //加载图片广告的ImageView
    private ImageView mAdImageView;
    private ImageLoaderUtil mLoader;
    private ImageRendererListener mImageRendererListener;

    /**
     * 构造方法用来创建广告View和数据
     *
     * @param parentView
     */
    public ImageAdRenderer(ViewGroup parentView) {
        mParentView = parentView;
        //获取应用的application做为上下文
        mContext = parentView.getContext();
        mLoader = ImageLoaderUtil.getInstance(mContext.getApplicationContext());
        initAdView();
    }

    @Override
    protected void initAdView() {
        mAdImageView = new ImageView(mContext);
        mAdImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        mAdImageView.setLayoutParams(layoutParams);
        mParentView.addView(mAdImageView);
    }


    @Override
    public void onShow(String uri) {
        mLoader.displayImage(mAdImageView,
                uri,
                new SimpleImageLoadingListener() {
                    /**
                     * 图片加载完成
                     */
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        if (mImageRendererListener != null) {
                            mImageRendererListener.onLoadingComplete();
                        }
                    }

                    /**
                     * 图片加载失败
                     */
                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        if (mImageRendererListener != null) {
                            mImageRendererListener.onLoadingFailed();
                        }
                    }
                }, ImageLoaderUtil.getInstance(mContext)
                        .getOptionsWithNoCache());
    }

    @Override
    public void onDispose() {
        mContext = null;
        mLoader = null;
        mAdImageView = null;
    }

    public void setImageRendererListener(ImageRendererListener listener) {
        mImageRendererListener = listener;
    }

    /**
     * 广告加载失败与否的回调
     */
    public interface ImageRendererListener {

        void onLoadingComplete(); //广告真正加载成功

        void onLoadingFailed(); //广告真正加载失败
    }
}
