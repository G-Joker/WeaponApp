package com.weapon.joker.app.message.post;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import android.widget.Toast;

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

    private Context mContext;

    public PostItemViewModel(Context context) {
        mContext = context;
    }

    /**
     * 公告item点击事件处理
     */
    public void onPostItemClick(View view){
        Toast.makeText(mContext, "点击了" + url, Toast.LENGTH_SHORT).show();
    }
}
