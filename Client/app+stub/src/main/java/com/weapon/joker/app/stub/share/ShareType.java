package com.weapon.joker.app.stub.share;

import java.io.Serializable;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.stub.share.ShareType
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/09/29
 *     desc   : 分享类型
 * </pre>
 */

public enum ShareType implements Serializable{

    QQ_ZONE("QQ 空间"), // qq 空间
    QQ("QQ"), // qq
    WEIXIN_CIRCLE("微信朋友圈"), // 微信朋友圈
    WEIXIN_FAVORITE("微信收藏"), // 微信收藏
    WEIXIN("微信"), // 微信
    WEIBO("新浪微博"), // 新浪微博
    OTHER("其他系统分享"), // 其他系统分享
    COPY("复制链接"); // 复制链接

    private String type;

    ShareType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
