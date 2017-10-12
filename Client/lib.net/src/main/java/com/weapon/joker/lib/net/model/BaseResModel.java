package com.weapon.joker.lib.net.model;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.net.model.BaseResModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/12
 *     desc   :
 * </pre>
 */

public class BaseResModel {
    public int status;
    public String desc = "";

    @Override
    public String toString() {
        return "BaseResModel{" + "status=" + status + ", desc='" + desc + '\'' + '}';
    }
}
