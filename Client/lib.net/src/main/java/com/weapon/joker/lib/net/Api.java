package com.weapon.joker.lib.net;

import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Api Retrofit Api
 * 参考修改：AndroidFire https://github.com/jaydenxiao2016/AndroidFire
 * author:张冠之
 * time: 2017/10/11 上午10:25
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class Api {
    /** 超时时长 单位：毫秒 */
    public static final int NET_TIME_OUT = 10000;
    /** 连接时长 单位：毫秒 */
    public static final int CONNECT_TIME_OUT = 10000;
    /** Retrofit 对象 */
    private Retrofit mRetrofit;
    /** Api 请求管理接口 */
    private ApiManager mApiManager;
    /** OkHttpClient 配置属性 */
    private OkHttpClient mOkHttpClient;
    /**
     * 用来存储 Api 的数据结构，SparseArray 是一种 Android 特有的数据结构，在千级数据
     * 以下比 HashMap 更省内存，它的 key 只能使用 int 型，没有装箱过程，查找数据的时候
     * 是通过二分查找，所以比 HashMap 在这种情况下更快性能更好
     */
    private static SparseArray<Api> sRetrofitManager = new SparseArray<>();

    /**
     * @param hostType 1-首页 2-消息 3-我的
     */
    public static ApiManager getDefault(int hostType) {
        Api retrofitManager = sRetrofitManager.get(hostType);
        if (retrofitManager == null) {
            retrofitManager = new Api(hostType);
            sRetrofitManager.put(hostType, retrofitManager);
        }
        retrofitManager.mApiManager = retrofitManager.mRetrofit.create(ApiManager.class);
        return retrofitManager.mApiManager;
    }

    /**
     * 私有构造 Api
     *
     * @param hostType host 类型 1-首页 2-消息 3-我的
     */
    private Api(int hostType) {

//        /** 缓存100MB */
//        File cacheFile = new File(BaseApplication.getAppContext().getCacheDir(), "cache");
//        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);

        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(NET_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
//                .addInterceptor(InterceptorUtil.CacheInterceptor())//缓存拦截器
//                .addNetworkInterceptor(InterceptorUtil.CacheInterceptor())//缓存拦截器
                .addInterceptor(InterceptorUtil.HeadInterceptor())//头部信息拦截器
                .addInterceptor(InterceptorUtil.LogInterceptor())//日志拦截器
//                .cache(cache)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiConstants.getHost(hostType))
                .build();
    }


}
