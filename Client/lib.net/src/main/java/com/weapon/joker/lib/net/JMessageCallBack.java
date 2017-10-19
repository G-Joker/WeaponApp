package com.weapon.joker.lib.net;

import cn.jpush.im.api.BasicCallback;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.net.JMessageCallBack
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/19
 *     desc   : JMessage 统一回调接口
 * </pre>
 */

public abstract class JMessageCallBack extends BasicCallback {

    @Override
    public void gotResult(int i, String s) {
        if (i == 0) {
            onSuccess();
        } else {
            onFailed(i, s);
        }
    }

    public abstract void onSuccess();
    public abstract void onFailed(int status, String desc);
}
