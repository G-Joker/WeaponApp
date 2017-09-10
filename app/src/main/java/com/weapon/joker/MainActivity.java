package com.weapon.joker;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.weapon.joker.lib.mvvm.common.BaseActivity;

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
    private Fragment mHomeFragment;
    private Fragment mMessageFragment;
    private Fragment mMineFragment;
    //当前 fragment
    private Fragment mCurrentFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initFragment();
        initViewPager();
        initTabLayout();
    }

    /**
     * 初始化 Fragment
     */
    private void initFragment() {

    }

    /**
     * 初始化 ViewPager
     */
    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.vp_main);
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
}
