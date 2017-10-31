package com.weapon.joker.app.message.post;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.weapon.joker.lib.middleware.WebViewActivity;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.post.PostItemViewModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/17
 *     desc   :
 * </pre>
 */

public class PostItemViewModel extends BaseObservable {

    @Bindable
    public String title = "title";
    @Bindable
    public String content = "content";
    @Bindable
    public String url = "url";
    @Bindable
    public String time = "";
    @Bindable
    public int messageId = 1;
    @Bindable
    public String imageUrl = "";

    private Context mContext;

    public PostItemViewModel(Context context) {
        mContext = context;
    }

    /**
     * 公告item点击事件处理
     */
    public void onPostItemClick(View view) {
        MobclickAgent.onEvent(mContext.getApplicationContext(), "post_item_click");
        WebViewActivity.startUrl(mContext, url);
    }
}
