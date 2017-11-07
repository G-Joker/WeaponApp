package com.weapon.joker.app.mine.about;

import android.content.Context;
import android.databinding.BaseObservable;
import android.text.TextUtils;
import android.view.View;

import com.weapon.joker.app.mine.R;
import com.weapon.joker.app.mine.about.model.OpenSourceLibrary;
import com.weapon.joker.lib.middleware.utils.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author : yueyang
 * date : 2017/10/28
 * e-mail: hi.yangyue1993@gmail.com
 */
public class AboutModel extends BaseObservable {

    private Context mContext;
    private static final String URL_PROJECT = "https://github.com/G-Joker/WeaponApp";
    private static final String TAG = AboutModel.class.getSimpleName();

    AboutModel(Context context) {
        mContext = context;
    }

    public View.OnClickListener clickFabFavorite() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/10/28 loadUrl
            }
        };
    }

    public List<OpenSourceLibrary> getOpenSourceLibraryList(Context context) {
        List<OpenSourceLibrary> openSourceLibraryList = new ArrayList<>();
        if (context == null) return openSourceLibraryList;
        String[] arrayName = context.getResources().getStringArray(R.array.array_open_source_name);
        String[] arrayLicenses = context.getResources().getStringArray(R.array.array_open_source_licenses);
        int arrayLength = arrayName.length;
        if (arrayLength > 0) {

            for (int i = 0; i < arrayLength; i++) {
                OpenSourceLibrary openSourceLibrary = new OpenSourceLibrary();
                try {
                    openSourceLibrary.setName(arrayName[i]);
                    openSourceLibrary.setLicenses(arrayLicenses[i]);
                } catch (IndexOutOfBoundsException e) {
                    LogUtils.d(TAG, "getOpenSourceLibraryList : " + e.getMessage());
                }
                openSourceLibraryList.add(openSourceLibrary);
            }
        }

        return openSourceLibraryList;
    }

    public View.OnClickListener OpenSourceLibraryClickLisener(final String url) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(url)) {
                    // TODO: 2017/11/4 loadUrl
                }
            }
        };
    }
}