package com.weapon.joker.app.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;

import com.weapon.joker.lib.middleware.PublicActivity;
import com.weapon.joker.lib.mvvm.common.BaseActivity;

import net.wequick.small.Small;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

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
        JMessageClient.registerEventReceiver(this);
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
        TabItemView messageTab = new TabItemView(this).setText(getString(R.string.message))
                                                      .setImageRes(R.drawable.selector_tab_main_message);
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


    @Override
    public void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    public void onEventMainThread(LoginStateChangeEvent event) {
        LoginStateChangeEvent.Reason reason = event.getReason();//获取变更的原因
        UserInfo myInfo = event.getMyInfo();//获取当前被登出账号的信息
        switch (reason) {
            case user_password_change:
                forcedExit("账号密码被修改");
                break;
            case user_logout:
                forcedExit("账号在另一台设备登录");
                break;
            case user_deleted:
                forcedExit("账号被删除");
                break;
            default:
                break;
        }
    }

    /**
     * 账号被强制退出
     * @param reason 被强制退出理由
     */
    private void forcedExit(String reason) {
        startActivity(new Intent(this, MainActivity.class));
        new AlertDialog.Builder(this).setTitle("请重新登录!")
                                     .setMessage(reason)
                                     .setNegativeButton("取消", null)
                                     .setPositiveButton("去登陆", new DialogInterface.OnClickListener() {
                                         @Override
                                         public void onClick(DialogInterface dialog, int which) {
                                             PublicActivity.startActivity(MainActivity.this,
                                                                          "com.weapon.joker.app.mine.login" + ".LoginRegisterFragment");
                                         }
                                     }).show();
    }

    /**
     * 点击通知消息的事件处理
     * @param event 收到的点击通知消息事件
     */
    public void onEvent(NotificationClickEvent event) {
        if (event == null) {
            return;
        }
        Message message = event.getMessage();
        Object targetInfo = message.getTargetInfo();
        if (targetInfo instanceof UserInfo) {
            String userName = message.getFromUser().getUserName();
            String displayName = message.getFromUser().getDisplayName();
            Intent intent = new Intent(this, PublicActivity.class);
            intent.putExtra("user_name", userName);
            intent.putExtra("display_name", displayName);
            PublicActivity.startActivity(this, "com.weapon.joker.app.message.single.SingleFragment", intent);
        } else if (targetInfo instanceof GroupInfo) {
            PublicActivity.startActivity(this, "com.weapon.joker.app.message.group.GroupFragment");
        }
    }

}
