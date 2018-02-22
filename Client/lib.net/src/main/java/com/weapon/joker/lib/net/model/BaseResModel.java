package com.weapon.joker.lib.net.model;

import com.google.gson.annotations.SerializedName;
import com.weapon.joker.lib.mvvm.common.BaseEntry;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.net.model.BaseResModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/12
 *     desc   :
 * </pre>
 */

public class BaseResModel<T> extends BaseEntry<T>{

    public static final int REQUEST_SUCCESS = 1000;
    @SerializedName("ecode")
    public int status;
    @SerializedName("emsg")
    public String desc = "";

    @Override
    public String toString() {
        return "BaseResModel{" + "status=" + status + ", desc='" + desc + '\'' + '}';
    }
}
