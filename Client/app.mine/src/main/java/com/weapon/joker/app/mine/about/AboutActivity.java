package com.weapon.joker.app.mine.about;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.weapon.joker.app.mine.R;
import com.weapon.joker.app.mine.databinding.ActivityAboutBinding;

/**
 * author : yueyang
 * date : 2017/10/28
 * e-mail: hi.yangyue1993@gmail.com
 */
public class AboutActivity extends AppCompatActivity {

    public static void launch(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAboutBinding aboutBinding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        aboutBinding.setAboutmodel(new AboutModel(this));
        initToolbar(aboutBinding);
    }

    private void initToolbar(ActivityAboutBinding aboutBinding) {
        setSupportActionBar(aboutBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
