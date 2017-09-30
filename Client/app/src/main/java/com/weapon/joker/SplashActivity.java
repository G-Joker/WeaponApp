package com.weapon.joker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import net.wequick.small.Small;


/**
 * SplashActivity 启动页
 * author:张冠之
 * time: 2017/9/10 下午3:13
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onStart() {
        super.onStart();

        Small.setUp(SplashActivity.this, new net.wequick.small.Small.OnCompleteListener() {
            @Override
            public void onComplete() {
                //延迟一秒启动插件
                getWindow().getDecorView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Small.openUri("main", SplashActivity.this);
                        finish();
                    }
                }, 1000);
            }
        });
    }
}
