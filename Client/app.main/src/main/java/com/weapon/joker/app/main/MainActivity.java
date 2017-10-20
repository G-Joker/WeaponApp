package com.weapon.joker.app.main;

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

        TabPagerAdapter mTabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        mViewPager.setAdapter(mTabPagerAdapter);
    }

    /**
     * 初始化 TabLayout
     */
    private void initTabLayout() {
        mTabLayout = findViewById(R.id.tab_layout);

        //设置 TabLayout 初始图像和字样
        TabItemView homeTab = new TabItemView(this).setText(getString(R.string.home)).setImageRes(R.drawable.selector_tab_main_home);
        TabItemView messageTab = new TabItemView(this).setText(getString(R.string.message)).setImageRes(R.drawable.selector_tab_main_message);
        TabItemView mineTab = new TabItemView(this).setText(getString(R.string.mine)).setImageRes(R.drawable.selector_tab_main_mine);

        mTabLayout.addTab(mTabLayout.newTab().setCustomView(homeTab));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(messageTab));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(mineTab));

        //自定义 View 的时候无法使用 setupWithViewPager() 方法
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    private class TabPagerAdapter extends FragmentPagerAdapter {

        private TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //构造插件 Fragment
            Fragment fragment = Small.createObject("fragment-v4", sUris[position], MainActivity.this);
            if (fragment == null) {
                fragment = PlaceHolderFragment.newInstance(position);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return sUris.length;
        }
    }

}
