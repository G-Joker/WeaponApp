package com.weapon.joker.app.mine.api;

import com.weapon.joker.app.mine.login.dataBean.LoginModel;
import com.weapon.joker.app.mine.login.dataBean.RegisterModel;
import com.weapon.joker.lib.net.ApiManager;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * MineApi 我的接口API
 * author:张冠之
 * time: 2018/2/20 下午11:03
 * e-mail: guanzhi.zhang@sojex.cn
 */

public interface MineApi extends ApiManager{
    /**
     * 登陆
     * params : name->用户名, password->密码
     * 在有多个字段的情况下，推荐使用这种方式
     */
    @GET("user/login")
    Observable<LoginModel> login(@QueryMap Map<String, Object> params);


    /**
     * 注册
     * @param name      用户名
     * @param password  密码
     */
    @GET ("user/register")
    Observable<RegisterModel> register(
            @Query("name") String name,
            @Query ("password") String password);

}
