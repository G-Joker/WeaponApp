package com.weapon.joker.lib.net;

import com.weapon.joker.lib.net.bean.HomeBean.HomeBean;
import com.weapon.joker.lib.net.bean.MineBean.LoginModel;
import com.weapon.joker.lib.net.bean.MineBean.RegisterModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * ApiManager 管理 Api 请求
 * author:张冠之
 * time: 2017/10/11 上午10:34
 * e-mail: guanzhi.zhang@sojex.cn
 */

public interface ApiManager {

    /**
     * 首页数据请求
     */
    @GET("home_data.json")
    Observable<HomeBean> getHomeListData();

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
