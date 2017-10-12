package com.weapon.joker.lib.middleware.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.middleware.view.CustomTextInputLayout
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/12
 *     desc   : 使用TextInputLayout中的setError功能时,不只会出现错误提示,
 *     而且会将EditText的背景渲染成提示文字的颜色,而一般又不需要这种样式,
 *     查看源码发现主要是因为调用了updateEditTextBackground()方法,只好重写了一些方法将颜色重置.
 * </pre>
 */

public class CustomTextInputLayout extends TextInputLayout {
    public CustomTextInputLayout(Context context) {
        super(context);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        recoverEditTextBackGround();
    }

    @Override
    public void setError(@Nullable CharSequence error) {
        super.setError(error);
        recoverEditTextBackGround();
    }

    /**
     * 将背景颜色重置
     */
    @SuppressLint ("RestrictedApi")
    private void recoverEditTextBackGround(){
        if (getEditText() == null) {
            return;
        }
        Drawable editTextBackground = getEditText().getBackground();
        if (editTextBackground == null) {
            return;
        }
        if (android.support.v7.widget.DrawableUtils.canSafelyMutateDrawable(editTextBackground)) {
            editTextBackground = editTextBackground.mutate();
        }
        DrawableCompat.clearColorFilter(editTextBackground);
    }
}
