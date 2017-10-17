package com.weapon.joker.app.message.post;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

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
}
