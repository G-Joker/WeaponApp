package com.weapon.joker.app.main;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * author : yueyang
 * date : 16/09/2017
 */
class PlaceHolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mList = new ArrayList<>();
    private static final int TYPE_VIEW_PAGER = 1111;
    private static final int TYPE_COMMON = 2222;
    private Context mContext;

    PlaceHolderAdapter(List<String> list) {
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case TYPE_VIEW_PAGER:
                holder = new ViewPagerHolder(inflater.inflate(R.layout.holder_pager, parent, false));
                break;
            case TYPE_COMMON:
                holder = new CommonHolder(inflater.inflate(R.layout.holder_common, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_VIEW_PAGER:
                ViewPagerHolder viewPagerHolder = (ViewPagerHolder) holder;
                viewPagerHolder.vp.setClipToPadding(false);
                viewPagerHolder.vp.setPageMargin(24);
                viewPagerHolder.vp.setAdapter(new HeaderPagerAdapter(mContext));
                break;
            case TYPE_COMMON:
                CommonHolder commonHolder = (CommonHolder) holder;
                String title = mList.get(position);
                commonHolder.tvTitle.setText(title);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_VIEW_PAGER : TYPE_COMMON;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class HeaderPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        int[] mResources = {R.drawable.place, R.drawable.place, R.drawable.place};

        HeaderPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.vp_header, container, false);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_pager);
            imageView.setImageResource(mResources[position]);
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }

    private class ViewPagerHolder extends RecyclerView.ViewHolder {

        ViewPager vp;

        ViewPagerHolder(View itemView) {
            super(itemView);
            vp = (ViewPager) itemView.findViewById(R.id.vp);
        }
    }

    private class CommonHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        CommonHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}