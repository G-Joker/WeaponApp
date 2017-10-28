package com.weapon.joker.app.mine.about;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

/**
 * author : yueyang
 * date : 2017/10/28
 * e-mail: hi.yangyue1993@gmail.com
 */
class AboutModel extends BaseObservable {

    private Context mContext;
    private static final String URL_PROJECT = "https://github.com/G-Joker/WeaponApp";

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
}

