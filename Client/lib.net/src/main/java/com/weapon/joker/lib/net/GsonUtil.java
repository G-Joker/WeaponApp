package com.weapon.joker.lib.net;

import com.google.gson.Gson;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.net.GsonUtil
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/13
 *     desc   : Gson 的单例模式
 * </pre>
 */

public class GsonUtil {
    private static Gson gson = new Gson();

    public static Gson getInstance() {
        return gson;
    }
}
