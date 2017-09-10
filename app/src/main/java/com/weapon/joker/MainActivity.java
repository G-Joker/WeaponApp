package com.weapon.joker;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.weapon.joker.lib.mvvm.common.BaseActivity;

import net.wequick.small.Small;

/**
 * MainActivity 创建首页所有的 fragment，以及
 * 启动模式 singleTask
 * author:张冠之
 * time: 2017/9/9 下午3:19
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class MainActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    /********** Fragment 相关 **********/
    private static String[] sUris = new String[]{"home", "message", "mine"};
    private TabPagerAdapter mTabPagerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initViewPager();
        initTabLayout();
    }

    /**
     * 初始化 ViewPager
     */
    private void initViewPager() {

        mTabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        mViewPager.setAdapter(mTabPagerAdapter);
    }

    /**
     * 初始化 TabLayout
     */
    private void initTabLayout() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);

        //设置 TabLayout 初始图像和字样
        TabItemView homeTab = new TabItemView(this).setText(getString(R.string.home)).setImageRes(R.drawable.selector_tab_main_home);
        TabItemView messageTab = new TabItemView(this).setText(getString(R.string.message)).setImageRes(R.drawable.selector_tab_main_message);
        TabItemView mineTab = new TabItemView(this).setText(getString(R.string.mine)).setImageRes(R.drawable.selector_tab_main_mine);

        mTabLayout.addTab(mTabLayout.newTab().setCustomView(homeTab));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(messageTab));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(mineTab));

        mTabLayout.addOnTabSelectedListener(new TabSelectedListener());

        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * 通过 Selected 状态改变 Tab 的图标和文字颜色
     */
    private class TabSelectedListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            tab.getCustomView().setSelected(true);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            tab.getCustomView().setSelected(false);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

    private class TabPagerAdapter extends FragmentPagerAdapter {

        private TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // TODO: 2017/9/10 fragment == null 情况处理
            //构造插件 Fragment
            return Small.createObject("fragment-v4", sUris[position], MainActivity.this);
        }

        @Override
        public int getCount() {
            return sUris.length;
        }
    }
}
