package com.weapon.joker.lib.middleware.utils;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.middleware.utils.Constants
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/09/28
 *     desc   :
 * </pre>
 */

public interface Constants {
    String QQ_APP_ID = "101426105";
    String QQ_SCOPE = "get_user_info";
    String QQ_REDIRECT_URL = "weapon.com"; // todo 换成真实的重定向链接
    String QQ_URL_OAUTH2_AUTHORIZATION_CODE = "https://graph.qq.com/oauth2.0/authorize?response_type=token&display=mobile&client_id=[YOUR_APPID]&redirect_uri=[YOUR_REDIRECT_URI]&scope=[THE_SCOPE]";
    String QQ_URL_OAUTH2_ACCESSTOKEN = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=[YOUR_APPID]&client_secret=[YOUR_APP_Key]&code=[The_AUTHORIZATION_CODE]&state=test&redirect_uri=[YOUR_REDIRECT_URI]";
    String QQ_URL_OAUTH2_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=[YOUR_ACCESS_TOKEN]";
    String QQ_URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?access_token=[YOUR_ACCESS_TOKEN]&oauth_consumer_key=[YOUR_APPID]&openid=[YOUR_OPENID]";







}
