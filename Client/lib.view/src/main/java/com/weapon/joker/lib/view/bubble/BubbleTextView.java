package com.weapon.joker.lib.view.bubble;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.weapon.joker.lib.view.R;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.view.bubble.BubbleTextView
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/23
 *     desc   : 气泡 TextView
 * </pre>
 */

public class BubbleTextView extends AppCompatTextView {
    private BubbleDrawable bubbleDrawable;
    private float mArrowWidth;
    private float mAngle;
    private float mArrowHeight;
    private float mArrowPosition;
    private int bubbleColor;
    private BubbleDrawable.ArrowLocation mArrowLocation;
    private boolean mArrowCenter;

    public BubbleTextView(Context context) {
        super(context);
        initView(null);
    }

    public BubbleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public BubbleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BubbleView);
            mArrowWidth = array.getDimension(R.styleable.BubbleView_arrowWidth, BubbleDrawable.Builder.DEFAULT_ARROW_WITH);
            mArrowHeight = array.getDimension(R.styleable.BubbleView_arrowHeight, BubbleDrawable.Builder.DEFAULT_ARROW_HEIGHT);
            mAngle = array.getDimension(R.styleable.BubbleView_angle, BubbleDrawable.Builder.DEFAULT_ANGLE);
            mArrowPosition = array.getDimension(R.styleable.BubbleView_arrowPosition, BubbleDrawable.Builder.DEFAULT_ARROW_POSITION);
            bubbleColor = array.getColor(R.styleable.BubbleView_bubbleColor, BubbleDrawable.Builder.DEFAULT_BUBBLE_COLOR);
            int location = array.getInt(R.styleable.BubbleView_arrowLocation, 0);
            mArrowLocation = BubbleDrawable.ArrowLocation.mapIntToValue(location);
            mArrowCenter = array.getBoolean(R.styleable.BubbleView_arrowCenter, false);
            array.recycle();
        }
        setUpPadding();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            setUp(w, h);
        }
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        setUp();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bubbleDrawable != null) {
            bubbleDrawable.draw(canvas);
        }
        super.onDraw(canvas);
    }

    private void setUp(int width, int height) {
        setUp(0, width, 0, height);
    }

    private void setUp() {
        setUp(getWidth(), getHeight());
    }

    private void setUp(int left, int right, int top, int bottom) {
        RectF rectF = new RectF(left, top, right, bottom);
        bubbleDrawable = new BubbleDrawable.Builder().rect(rectF)
                                                     .arrowLocation(mArrowLocation)
                                                     .bubbleType(BubbleDrawable.BubbleType.COLOR)
                                                     .angle(mAngle)
                                                     .arrowHeight(mArrowHeight)
                                                     .arrowWidth(mArrowWidth)
                                                     .bubbleColor(bubbleColor)
                                                     .arrowPosition(mArrowPosition)
                                                     .arrowCenter(mArrowCenter)
                                                     .build();
    }

    private void setUpPadding() {
        int left = getPaddingLeft();
        int right = getPaddingRight();
        int top = getPaddingTop();
        int bottom = getPaddingBottom();
        switch (mArrowLocation) {
            case LEFT:
                left += mArrowWidth;
                break;
            case RIGHT:
                right += mArrowWidth;
                break;
            case TOP:
                top += mArrowHeight;
                break;
            case BOTTOM:
                bottom += mArrowHeight;
                break;
            default:
                break;
        }
        setPadding(left, top, right, bottom);
    }

}
