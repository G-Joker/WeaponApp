package com.weapon.joker.app.home.header;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weapon.joker.app.home.R;
import com.weapon.joker.lib.net.bean.HomeBean.RecommandFooterValue;
import com.weapon.joker.lib.net.bean.HomeBean.RecommandHeadValue;
import com.weapon.joker.lib.view.viewpagerindictor.CirclePageIndicator;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;


/**
 * @author: vision
 * @function:
 * @date: 16/9/2
 */
public class HomeHeaderLayout extends RelativeLayout {

    private Context mContext;

    /**
     * UI
     */
    private RelativeLayout mRootView;
    private AutoScrollViewPager mViewPager;
    private CirclePageIndicator mPagerIndictor;
    private TextView mHotView;
    private PhotoPagerAdapter mAdapter;
    private ImageView[] mImageViews = new ImageView[4];
    private LinearLayout mFootLayout;

    /**
     * Data
     */
    private RecommandHeadValue mHeaderValue;

    public HomeHeaderLayout(Context context) {
        this(context, null);
    }

    public HomeHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public void setData(RecommandHeadValue data){
        mHeaderValue = data;
        mAdapter = new PhotoPagerAdapter(mContext, mHeaderValue.ads, true);
        mViewPager.setAdapter(mAdapter);
        mViewPager.startAutoScroll(3000);
        mPagerIndictor.setViewPager(mViewPager);

        for (int i = 0; i < mImageViews.length; i++) {
            Glide.with(mContext).load(mHeaderValue.middle.get(i)).into(mImageViews[i]);
        }

        for (RecommandFooterValue value : mHeaderValue.footer) {
            mFootLayout.addView(createItem(value));
        }
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mRootView = (RelativeLayout) inflater.inflate(R.layout.item_home_head_layout, this);
        mViewPager = (AutoScrollViewPager) mRootView.findViewById(R.id.pager);
        mPagerIndictor = (CirclePageIndicator) mRootView.findViewById(R.id.pager_indictor_view);
        mHotView = (TextView) mRootView.findViewById(R.id.zuixing_view);
        mImageViews[0] = (ImageView) mRootView.findViewById(R.id.head_image_one);
        mImageViews[1] = (ImageView) mRootView.findViewById(R.id.head_image_two);
        mImageViews[2] = (ImageView) mRootView.findViewById(R.id.head_image_three);
        mImageViews[3] = (ImageView) mRootView.findViewById(R.id.head_image_four);
        mFootLayout = (LinearLayout) mRootView.findViewById(R.id.content_layout);
        mHotView.setText(mContext.getString(R.string.today_zuixing));
    }

    private HomeBottomItem createItem(RecommandFooterValue value) {
        HomeBottomItem item = new HomeBottomItem(mContext, value);
        return item;
    }

    @BindingAdapter("headValue")
    public static void setData(HomeHeaderLayout view,RecommandHeadValue data){
        if (data!=null) {
            view.setData(data);
        }
    }
}
