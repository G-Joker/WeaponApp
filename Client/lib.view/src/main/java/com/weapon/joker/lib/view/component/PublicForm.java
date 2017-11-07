package com.weapon.joker.lib.view.component;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weapon.joker.lib.view.R;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.view.component.PublicForm
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/30
 *     desc   :
 * </pre>
 */

public class PublicForm extends RelativeLayout {

    private TextView mTvTitle;
    private TextView mTvContent;

    public PublicForm(Context context) {
        this(context, null);
    }

    public PublicForm(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PublicForm(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);
    }

    private void initView(Context context, AttributeSet attrs, int defStyle) {
        View view = View.inflate(context, R.layout.public_form, this);
        mTvContent = view.findViewById(R.id.tv_content);
        mTvTitle = view.findViewById(R.id.tv_title);
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setText(title);
        }
    }

    public void setContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            mTvContent.setText(content);
        }
    }

}
