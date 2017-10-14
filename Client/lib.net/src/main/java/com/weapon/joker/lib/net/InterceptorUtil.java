package com.weapon.joker.lib.net;

import com.weapon.joker.lib.middleware.utils.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * InterceptorUtil 拦截器工具类
 * author:张冠之
 * time: 2017/10/11 下午3:52
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class InterceptorUtil {
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

    /** 设缓存有效期为两天 */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    /** 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     *  max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。*/
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    /** 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器*/
    private static final String CACHE_CONTROL_AGE = "max-age=0";



    /** 日志拦截器 */
    static HttpLoggingInterceptor LogInterceptor(){
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){

            @Override
            public void log(String message) {
                LogUtils.logw(message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);//设置打印数据的级别
    }



    /** 云端响应头拦截器，用来配置缓存策略 */
//    static Interceptor CacheInterceptor(){
//        return new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                /** 添加统一的请求头缓存策略 */
//                Request request = chain.request();
//                request = request.newBuilder()
//                        .cacheControl(NetWorkUtils.isNetConnected(BaseApplication.getAppContext()) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
//                        .build();
//
//                /** Response 缓存配置 */
//                Response originalResponse = chain.proceed(request);
//                if (NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
//                    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
//
//                    return originalResponse.newBuilder()
//                            .header("Cache-Control", CACHE_CONTROL_AGE)
//                            .removeHeader("Pragma")
//                            .build();
//                } else {
//                    return originalResponse.newBuilder()
//                            .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
//                            .removeHeader("Pragma")
//                            .build();
//                }
//            }
//        };
//    }



    /** 增加头部信息 */
    static Interceptor HeadInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(build);
            }
        };
    }
}
