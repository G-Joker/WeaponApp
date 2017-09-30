package com.weapon.joker.app.message;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * GetRequest_Interface Retrofit 描述网络请求
 * author:张冠之
 * time: 2017/9/30 下午2:40
 * e-mail: guanzhi.zhang@sojex.cn
 */

public interface GetRequest_Interface {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<MessageModel> getCall();
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // getCall()是接受网络请求数据的方法
}
