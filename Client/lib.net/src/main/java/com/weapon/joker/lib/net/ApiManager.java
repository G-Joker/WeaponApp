package com.weapon.joker.lib.net;

import com.weapon.joker.lib.net.model.LoginModel;
import com.weapon.joker.lib.net.bean.MessageBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * ApiManager 管理 Api 请求
 * author:张冠之
 * time: 2017/10/11 上午10:34
 * e-mail: guanzhi.zhang@sojex.cn
 */

public interface ApiManager {
    @GET ("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Observable<MessageBean> getCall();

    /**
     * 登陆
     * @param name      用户名
     * @param password  用户密码
     */
    @GET ("user/login")
    Observable<LoginModel> login(
            @Query ("name") String name,
            @Query ("password") String password);

}
