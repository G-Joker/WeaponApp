package com.weapon.joker.lib.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.view.SlideView
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/27
 *     desc   :
 * </pre>
 */

public class SlideView extends LinearLayout implements AppBarLayout.OnOffsetChangedListener {

    private View avatarView;
    private TextView titleView;

    private float collapsedPadding;
    private float expandedPadding;

    private float expandedImageSize;
    private float collapsedImageSize;

    private boolean valuesCalculatedAlready = false;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private float toolBarHeight;
    private float expandedHeight;
    private float maxOffset;

    public SlideView(Context context) {
        this(context, null);
        init();
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AvatarView);
        try {
            collapsedPadding = a.getDimension(R.styleable.AvatarView_collapsedPadding, -1);
            expandedPadding = a.getDimension(R.styleable.AvatarView_expandedPadding, -1);

            collapsedImageSize = a.getDimension(R.styleable.AvatarView_collapsedImageSize, -1);
            expandedImageSize = a.getDimension(R.styleable.AvatarView_expandedImageSize, -1);

        } finally {
            a.recycle();
        }

        final Resources resources = getResources();

        if (collapsedImageSize < 0) {
            collapsedImageSize = resources.getDimension(R.dimen.default_collapsed_image_size);
        }
        if (expandedImageSize < 0) {
            expandedImageSize = resources.getDimension(R.dimen.default_expanded_image_size);
        }

        if (collapsedPadding < 0) {
            collapsedPadding = resources.getDimension(R.dimen.default_collapsed_padding);
        }
        if (expandedPadding < 0) {
            expandedPadding = resources.getDimension(R.dimen.default_expanded_padding);
        }
    }

    private void init() {
        setOrientation(HORIZONTAL);
    }


    @NonNull
    private AppBarLayout findParentAppBarLayout() {
        ViewParent parent = this.getParent();
        if (parent instanceof AppBarLayout) {
            return ((AppBarLayout) parent);
        } else if (parent.getParent() instanceof AppBarLayout) {
            return ((AppBarLayout) parent.getParent());
        } else {
            throw new IllegalStateException("Must be inside an AppBarLayout"); //TODO actually, a collapsingtoolbar
        }
    }

    //此方法在onDraw方法之前调用，也就是view还没有画出来的时候，可以在此方法中去执行一些初始化的操作
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        findViews();
        if (!isInEditMode()) {
            appBarLayout.addOnOffsetChangedListener(this);
        } else {
            setExpandedValuesForEditMode();
        }
    }

    private void setExpandedValuesForEditMode() {
        calculateValues();
        updateViews(1f, 0);
    }

    private void findViews() {
        appBarLayout = findParentAppBarLayout();
        toolbar = findSiblingToolbar();
        avatarView = findAvatar();
        titleView = findTitle();
    }

    private View findAvatar() {
        View avatar = null;
        if (getChildAt(0) instanceof ImageView) {
            avatar = getChildAt(0);
        }
        return avatar;
    }

    private TextView findTitle() {
        TextView title = null;
        if (getChildAt(0) instanceof TextView) {
            title = (TextView) getChildAt(0);
        }
        return title;
    }

    //使用@NonNull注解修饰的参数不能为null
    @NonNull
    private Toolbar findSiblingToolbar() {
        ViewGroup parent = ((ViewGroup) this.getParent());
        for (int i = 0, c = parent.getChildCount(); i < c; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof Toolbar) {
                return (Toolbar) child;
            }
        }
        throw new IllegalStateException("No toolbar found as sibling");
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        if (!valuesCalculatedAlready) {
            calculateValues();
            valuesCalculatedAlready = true;
        }
        float expandedPercentage = 1 - (-offset / maxOffset);
        updateViews(expandedPercentage, offset);
    }

    private void calculateValues() {
        toolBarHeight = toolbar.getHeight();
        expandedHeight = appBarLayout.getHeight() - toolbar.getHeight();
        maxOffset = expandedHeight;
        //使控件处在居中的位置
        //expandedPadding 布局最开始距离左侧的padding
        if (avatarView != null) {
            expandedPadding = (appBarLayout.getWidth() - expandedImageSize) / 2;
        } else if (titleView != null) {
            expandedPadding = (appBarLayout.getWidth() - titleView.getWidth()) / 2;
        }
    }


    //expandHeight   appbar的最大移动距离
    //toolBarHeight 固定在顶上的toolbar的高度
    //expandedPercentage 1-0  inversePercentage 0-1
    //collapsed 折叠时的    //expanded  展开时的，初始的
    private void updateViews(float expandedPercentage, int currentOffset) {
        float inversePercentage = 1 - expandedPercentage;
        float translation = -currentOffset + ((float) toolbar.getHeight() * expandedPercentage);
        float currHeight = 0;

        if (avatarView != null) {
            currHeight = toolBarHeight + (expandedHeight - toolBarHeight) / 4 * expandedPercentage;
        } else if (titleView != null) {
            currHeight = toolBarHeight + (expandedHeight - toolBarHeight / 2) * expandedPercentage;
        }
        float currentPadding = expandedPadding + (collapsedPadding - expandedPadding) * inversePercentage;
        float currentImageSize = collapsedImageSize + (expandedImageSize - collapsedImageSize) * expandedPercentage;

        setContainerOffset(translation);
        setContainerHeight((int) currHeight);
        setPadding((int) currentPadding);
        setAvatarSize((int) currentImageSize);
    }

    private void setContainerOffset(float translation) {
        this.setTranslationY(translation);
    }

    private void setContainerHeight(int currHeight) {
        this.getLayoutParams().height = currHeight;
    }

    private void setPadding(int currentPadding) {
        this.setPadding(currentPadding, 0, 0, 0);
    }

    private void setAvatarSize(int currentImageSize) {
        if (avatarView != null) {
            avatarView.getLayoutParams().height = currentImageSize;
            avatarView.getLayoutParams().width = currentImageSize;
        }
    }
}
