package com.weapon.joker.lib.video.widget.adbrowser;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.weapon.joker.lib.video.adutil.LogUtils;

import java.net.URISyntaxException;

/**
 * Custom WebViewClient for AdBrowserWebView which handles different url schemes.
 * Has listener to communicate with buttons on AdBrowserLayout.
 */
public class AdBrowserWebViewClient extends WebViewClient {

    private static final String LOG_TAG = AdBrowserWebViewClient.class.getSimpleName();

    public static final String PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=";

    private static final String HEADER_PLAIN_TEXT = "plain/text";

    private static final String TEL_SCHEME = "tel";
    private static final String MAILTO_SCHEME = "mailto";
    private static final String GEO_SCHEME = "geo";
    private static final String MARKET_SCHEME = "market";
    private static final String YOUTUBE_SCHEME = "vnd.youtube";
    private static final String HTTP_SCHEME = "http";
    private static final String HTTPS_SCHEME = "https";
    private static final String INTENT_SCHEME = "intent";

    private static final String GEO_HOST = "maps.google.com";
    private static final String MARKET_HOST = "play.google.com";
    private static final String YOUTUBE_HOST1 = "www.youtube.com";
    private static final String YOUTUBE_HOST2 = "m.youtube.com";

    private Listener mListener;

    private Listener mEmptyListener = new Listener() {
        @Override
        public void onPageStarted() {
        }

        @Override
        public void onPageFinished(boolean canGoBack) {
        }

        @Override
        public void onReceiveError() {
        }

        @Override
        public void onLeaveApp() {
        }
    };

    public interface Listener {
        void onPageStarted();

        void onPageFinished(boolean canGoBack);

        void onReceiveError();

        void onLeaveApp();
    }

    public AdBrowserWebViewClient(Listener listener) {
        if (listener == null) {
            LogUtils.i(LOG_TAG, "Error: Wrong listener");
            mListener = mEmptyListener;
        } else {
            mListener = listener;
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LogUtils.i(LOG_TAG, "shouldOverrideUrlLoading url=" + url);
        Context context = view.getContext();

        Uri uri;
        try {
            uri = Uri.parse(url);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            return false;
        }
        if (uri == null) {
            return false;
        }

        String scheme = uri.getScheme();
        String host = uri.getHost();

        if (TextUtils.isEmpty(scheme)) {
            return false;
        }

        if (scheme.equalsIgnoreCase(TEL_SCHEME)) {
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            resolveAndStartActivity(intent, context);

        } else if (scheme.equalsIgnoreCase(MAILTO_SCHEME)) {
            url = url.replaceFirst("mailto:", "");
            url = url.trim();
            Intent intent = new Intent(Intent.ACTION_SEND, uri);
            intent.setType(HEADER_PLAIN_TEXT).putExtra(Intent.EXTRA_EMAIL, new String[]{url});
            resolveAndStartActivity(intent, context);

        } else if (scheme.equalsIgnoreCase(GEO_SCHEME)) {
            Intent searchAddress = new Intent(Intent.ACTION_VIEW, uri);
            resolveAndStartActivity(searchAddress, context);

        } else if (scheme.equalsIgnoreCase(YOUTUBE_SCHEME)) {
            leaveApp(url, context);

        } else if (scheme.equalsIgnoreCase(HTTP_SCHEME)
                || scheme.equalsIgnoreCase(HTTPS_SCHEME)) {
            return checkHost(url, host, context);

        } else if (scheme.equalsIgnoreCase(INTENT_SCHEME)) {
            handleIntentScheme(url, context);
        } else if (scheme.equalsIgnoreCase(MARKET_SCHEME)) {
            handleMarketScheme(url, context);
        } else {
            return true;
        }

        return true;
    }

    /**
     * Checks host
     *
     * @param url  - full url
     * @param host - host from url
     * @return true - if param host equals with geo, market or youtube host
     * false - otherwise
     */
    private boolean checkHost(String url, String host, Context context) {
        if (host.equalsIgnoreCase(GEO_HOST)) {
            Intent searchAddress = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            resolveAndStartActivity(searchAddress, context);

        } else if (host.equalsIgnoreCase(MARKET_HOST)
                || host.equalsIgnoreCase(YOUTUBE_HOST1)
                || host.equalsIgnoreCase(YOUTUBE_HOST2)) {
            leaveApp(url, context);

        } else {
            return false;
        }
        return true;
    }

    private void handleMarketScheme(String url, Context context) {
        try {
            Intent intent = Intent.parseUri(url, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (isActivityResolved(intent, context)) {
                context.startActivity(intent);
            } else {
                Uri uri = Uri.parse(url);
                String id = uri.getQueryParameter("id");
                url = PLAY_STORE_URL + id;
                leaveApp(url, context);
            }
        } catch (Exception e) {
            mListener.onReceiveError();
        }
    }

    private void handleIntentScheme(String url, Context context) {
        try {
            Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (isActivityResolved(intent, context)) {
                context.startActivity(intent);
            } else {
                url = PLAY_STORE_URL + intent.getPackage();
                leaveApp(url, context);
            }
        } catch (URISyntaxException e) {
            mListener.onReceiveError();
        }
    }

    private void leaveApp(String url, Context context) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        resolveAndStartActivity(intent, context);
        mListener.onLeaveApp();
    }

    private void resolveAndStartActivity(Intent intent, Context context) {
        if (isActivityResolved(intent, context)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            mListener.onReceiveError();
        }
    }

    private boolean isActivityResolved(Intent intent, Context context) {
        return context.getPackageManager()
                .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null;
    }

    @Override
    public final void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        mListener.onPageStarted();
    }

    @Override
    public final void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        mListener.onPageFinished(view.canGoBack());
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        String mess = "onReceivedError: " + description;
        LogUtils.i(LOG_TAG, mess);
        mListener.onReceiveError();
    }
}
