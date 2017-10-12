package com.weapon.joker.lib.net.bean;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.net.bean.UserBean
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/12
 *     desc   : 用户 bean
 * </pre>
 */

public class UserBean {
    public String user;
    public String token;
    public String uid;

    @Override
    public String toString() {
        return "UserBean{" + "user='" + user + '\'' + ", token='" + token + '\'' + ", uid='" + uid + '\'' + '}';
    }
}
