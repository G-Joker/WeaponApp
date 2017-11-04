package com.weapon.joker.app.mine.about;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weapon.joker.app.mine.R;
import com.weapon.joker.app.mine.about.model.OpenSourceLibrary;
import com.weapon.joker.app.mine.databinding.ActivityAboutBinding;
import com.weapon.joker.lib.middleware.utils.Util;

import java.util.List;

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

    private AboutModel mAboutModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAboutBinding aboutBinding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        mAboutModel = new AboutModel(this);
        aboutBinding.setAboutmodel(mAboutModel);
        initToolbar(aboutBinding);
        initOpenSourceLibrary(aboutBinding, mAboutModel);
    }

    private void initToolbar(ActivityAboutBinding aboutBinding) {
        setSupportActionBar(aboutBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     *  初始化Open Source Licenses相关的信息
     * author: yueyang
     **/
    private void initOpenSourceLibrary(ActivityAboutBinding aboutBinding, AboutModel aboutModel) {
        if (aboutModel == null) {
            return;
        }

        List<OpenSourceLibrary> openSourceList = aboutModel.getOpenSourceLibraryList(this);
        if (openSourceList == null || openSourceList.size() <= 0) {
            return;
        }

        for (OpenSourceLibrary openSourceLibrary : openSourceList) {
            LinearLayout llOpenSource = aboutBinding.llOpenSource;
            dynamicAddOpenSourceItem(llOpenSource, openSourceLibrary);
        }
    }

    private void dynamicAddOpenSourceItem(LinearLayout rootView, OpenSourceLibrary sourceLibrary) {
        if (rootView == null || sourceLibrary == null) {
            throw new IllegalArgumentException("rootVie or sourceLibrary cannot be null");
        }

        if (!TextUtils.isEmpty(sourceLibrary.getName())) {
            LinearLayout.LayoutParams paramsOpenSourceName = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsOpenSourceName.topMargin = Util.dip2px(this, 10);
            TextView tvOpenSourceName = new TextView(this);
            tvOpenSourceName.setTextSize(14);
            tvOpenSourceName.setLayoutParams(paramsOpenSourceName);
            tvOpenSourceName.setText(sourceLibrary.getName());
            rootView.addView(tvOpenSourceName);
            if (mAboutModel != null) {
                tvOpenSourceName.setOnClickListener(mAboutModel.OpenSourceLibraryClickLisener(sourceLibrary.getLink()));
            }
        }

        if (!TextUtils.isEmpty(sourceLibrary.getLicenses())) {
            LinearLayout.LayoutParams paramsOpenSourceLicenses = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tvOpenSourceLicenses = new TextView(this);
            tvOpenSourceLicenses.setTextSize(12);
            tvOpenSourceLicenses.setTextColor(getResources().getColor(R.color.grey500));
            tvOpenSourceLicenses.setLayoutParams(paramsOpenSourceLicenses);
            tvOpenSourceLicenses.setText(sourceLibrary.getLicenses());
            if (mAboutModel != null) {
                tvOpenSourceLicenses.setOnClickListener(mAboutModel.OpenSourceLibraryClickLisener(sourceLibrary.getLink()));
            }
            rootView.addView(tvOpenSourceLicenses);
        }
    }
}