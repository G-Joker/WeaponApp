package com.weapon.joker.lib.net;

import com.weapon.joker.lib.net.bean.MessageModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * ApiManager 管理 Api 请求
 * author:张冠之
 * time: 2017/10/11 上午10:34
 * e-mail: guanzhi.zhang@sojex.cn
 */

public interface ApiManager {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Observable<MessageModel> getCall();
}
