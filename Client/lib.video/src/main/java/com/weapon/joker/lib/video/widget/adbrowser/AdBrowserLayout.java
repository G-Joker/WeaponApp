package com.weapon.joker.lib.video.widget.adbrowser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.weapon.joker.lib.video.adutil.Utils;


/**
 * 广告页面布局
 */
public final class AdBrowserLayout extends RelativeLayout {

    private static final int HEADER_HEIGHT_DP = 45;

    private final RelativeLayout mFooterView;
    private final ProgressBar mProgressBar;
    private final Button mBackBtn;
    private final Button mRefreshBtn;
    private final Button mCloseBtn;
    private final Button mNativeBtn;
    private final BrowserWebView mAdBrowserWebview;

    private Base64Drawables mBase64Drawables = new Base64Drawables();

    public AdBrowserLayout(Context context) {
        super(context);
        LayoutParams params = new LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);

        mAdBrowserWebview = new BrowserWebView(context);
        mAdBrowserWebview.setLayoutParams(params);
        addView(mAdBrowserWebview);

        mFooterView = new RelativeLayout(context);
        configFooterView();

        LinearLayout buttonsContainer = new LinearLayout(context);
        configButtonsContainer(buttonsContainer);

        int buttonWidth = Utils.getDisplayMetrics(context).widthPixels / 5;
        LayoutParams buttons_params = new LayoutParams(
            buttonWidth, ViewGroup.LayoutParams.MATCH_PARENT);

        int size = Utils.dip2px(context,
            HEADER_HEIGHT_DP) / 2;
        LayoutParams pb_params = new LayoutParams(size, size);
        pb_params.addRule(RelativeLayout.CENTER_IN_PARENT);

        mProgressBar = new ProgressBar(context);
        configProgressButton(context, buttonsContainer, buttons_params, pb_params);

        mBackBtn = new Button(context);
        configBackButton(context, buttonsContainer, buttons_params, pb_params);

        mRefreshBtn = new Button(context);
        configRefreshButton(context, buttonsContainer, buttons_params, pb_params);

        mNativeBtn = new Button(context);
        configNativeButton(context, buttonsContainer, buttons_params, pb_params);

        mCloseBtn = new Button(context);
        configCloseButton(context, buttonsContainer, buttons_params, pb_params);

        mFooterView.addView(initBottomWhiteLineView(context));
    }

    private void configProgressButton(Context context,
                                      LinearLayout buttonsContainer,
                                      LayoutParams buttons_params,
                                      LayoutParams pb_params) {
        RelativeLayout progressLayout = new RelativeLayout(context);
        progressLayout.setLayoutParams(buttons_params);
        mProgressBar.setLayoutParams(pb_params);
        progressLayout.addView(mProgressBar);
        buttonsContainer.addView(progressLayout);
    }

    private void configButtonsContainer(LinearLayout buttonsContainer) {
        LinearLayout.LayoutParams buttonsContainerParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        buttonsContainer.setLayoutParams(buttonsContainerParams);
        buttonsContainer.setBackgroundColor(Color.BLACK);
        mFooterView.addView(buttonsContainer);
    }

    private void configFooterView() {
        LayoutParams footer_params = new LayoutParams(
            LayoutParams.MATCH_PARENT,
            Utils.dip2px(getContext(), HEADER_HEIGHT_DP));
        footer_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mFooterView.setLayoutParams(footer_params);
        addView(mFooterView);
    }

    private View initBottomWhiteLineView(Context context) {
        View whiteLine = new View(context);
        LayoutParams whiteLineParams = new LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 1);
        whiteLineParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        whiteLine.setLayoutParams(whiteLineParams);
        whiteLine.setBackgroundColor(Color.WHITE);
        return whiteLine;
    }

    @SuppressLint("NewApi")
    private void configBackButton(Context context, LinearLayout buttonsContainer,
                                  LayoutParams buttons_params,
                                  LayoutParams pb_params) {
        RelativeLayout backLayout = new RelativeLayout(context);
        backLayout.setLayoutParams(buttons_params);
        if (Build.VERSION.SDK_INT < 16) {
            mBackBtn.setBackgroundDrawable(Utils.decodeImage(mBase64Drawables.getBackInactive()));
        } else {
            mBackBtn.setBackground(Utils.decodeImage(mBase64Drawables.getBackInactive()));
        }
        mBackBtn.setLayoutParams(pb_params);
        backLayout.addView(mBackBtn);
        buttonsContainer.addView(backLayout);
    }

    @SuppressLint("NewApi")
    private void configRefreshButton(Context context, LinearLayout buttonsContainer,
                                     LayoutParams buttons_params,
                                     LayoutParams pb_params) {
        RelativeLayout refreshLayout = new RelativeLayout(context);
        refreshLayout.setLayoutParams(buttons_params);
        if (Build.VERSION.SDK_INT < 16) {
            mRefreshBtn.setBackgroundDrawable(Utils.decodeImage(mBase64Drawables.getRefresh()));
        } else {
            mRefreshBtn.setBackground(Utils.decodeImage(mBase64Drawables.getRefresh()));
        }
        mRefreshBtn.setLayoutParams(pb_params);
        refreshLayout.addView(mRefreshBtn);
        buttonsContainer.addView(refreshLayout);
    }

    @SuppressLint("NewApi")
    private void configNativeButton(Context context, LinearLayout buttonsContainer,
                                    LayoutParams buttons_params,
                                    LayoutParams pb_params) {
        RelativeLayout nativeLayout = new RelativeLayout(context);
        nativeLayout.setLayoutParams(buttons_params);
        if (Build.VERSION.SDK_INT < 16) {
            mNativeBtn.setBackgroundDrawable(Utils.decodeImage(mBase64Drawables.getNativeBrowser()));
        } else {
            mNativeBtn.setBackground(Utils.decodeImage(mBase64Drawables.getNativeBrowser()));
        }
        mNativeBtn.setLayoutParams(pb_params);
        nativeLayout.addView(mNativeBtn);
        buttonsContainer.addView(nativeLayout);
    }

    @SuppressLint("NewApi")
    private void configCloseButton(Context context, LinearLayout buttonsContainer,
                                   LayoutParams buttons_params,
                                   LayoutParams pb_params) {
        RelativeLayout closeLayout = new RelativeLayout(context);
        closeLayout.setLayoutParams(buttons_params);
        if (Build.VERSION.SDK_INT < 16) {
            mCloseBtn.setBackgroundDrawable(Utils.decodeImage(mBase64Drawables.getClose()));
        } else {
            mCloseBtn.setBackground(Utils.decodeImage(mBase64Drawables.getClose()));
        }
        mCloseBtn.setLayoutParams(pb_params);
        closeLayout.addView(mCloseBtn);
        buttonsContainer.addView(closeLayout);
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    public Button getBackButton() {
        return mBackBtn;
    }

    public Button getRefreshButton() {
        return mRefreshBtn;
    }

    public Button getCloseButton() {
        return mCloseBtn;
    }

    public Button getNativeButton() {
        return mNativeBtn;
    }

    public BrowserWebView getWebView() {
        return mAdBrowserWebview;
    }
}
