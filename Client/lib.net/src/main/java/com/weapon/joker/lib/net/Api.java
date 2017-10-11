package com.weapon.joker.lib.net;

import android.content.Context;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weapon.joker.lib.middleware.utils.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Api Retrofit Api
 * 参考修改：AndroidFire https://github.com/jaydenxiao2016/AndroidFire
 * author:张冠之
 * time: 2017/10/11 上午10:25
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class Api {
    /**
     * 超时时长 单位：毫秒
     */
    public static final int NET_TIME_OUT = 10000;
    /**
     * 连接时长 单位：毫秒
     */
    public static final int CONNECT_TIME_OUT = 10000;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * Retrofit 对象
     */
    private Retrofit mRetrofit;
    /**
     * Api 请求管理接口
     */
    private ApiManager mApiManager;
    /**
     * OkHttpClient 配置属性
     */
    private OkHttpClient mOkHttpClient;
    /**
     * 用来存储 Api 的数据结构，SparseArray 是一种 Android 特有的数据结构，在千级数据
     * 以下比 HashMap 更省内存，它的 key 只能使用 int 型，没有装箱过程，查找数据的时候
     * 是通过二分查找，所以比 HashMap 在这种情况下更快性能更好
     */
    private static SparseArray<Api> sRetrofitManager = new SparseArray<>();

    /************************* 缓存相关 *********************/

    /**
     *  1. noCache 不使用缓存，全部走网络

     2. noStore 不使用缓存，也不存储缓存

     3. onlyIfCached 只使用缓存

     4. maxAge 设置最大失效时间，失效则不使用 需要服务器配合

     5. maxStale 设置最大失效时间，失效则不使用 需要服务器配合 感觉这两个类似 还没怎么弄清楚，清楚的同学欢迎留言

     6. minFresh 设置有效时间，依旧如上

     7. FORCE_NETWORK 只走网络

     8. FORCE_CACHE 只走缓存
     */

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";

    /**
     * 私有构造 Api
     *
     * @param hostType host 类型 1-首页 2-消息 3-我的
     */
    private Api(int hostType) {
        /** 开启 LOG */
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        /** 缓存100MB */
        File cacheFile = new File(mContext.getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);

        /** 增加头部信息 */
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(build);
            }
        };

        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(NET_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(headerInterceptor)
                .addInterceptor(logInterceptor)
                .cache(cache)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ApiConstants.getHost(hostType))
                .build();
        mApiManager = mRetrofit.create(ApiManager.class);
    }

    /**
     * @param hostType 1-首页 2-消息 3-我的
     */
    public static ApiManager getDefault(int hostType) {
        Api retrofitManager = sRetrofitManager.get(hostType);
        if (retrofitManager == null) {
            retrofitManager = new Api(hostType);
            sRetrofitManager.put(hostType, retrofitManager);
        }
        return retrofitManager.mApiManager;
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            /** 添加统一的请求头缓存策略 */
            Request request = chain.request();
            request = request.newBuilder()
                    .cacheControl(NetWorkUtils.isNetConnected(mContext) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                    .build();

            /** Response 缓存配置 */
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.isNetConnected(mContext)) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置

                return originalResponse.newBuilder()
                        .header("Cache-Control", CACHE_CONTROL_AGE)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };
}
