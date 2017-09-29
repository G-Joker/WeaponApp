package com.weapon.joker.app.stub.share;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.stub.share.ShareType
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/09/29
 *     desc   : 分享类型
 * </pre>
 */

public enum ShareType {

    QQ_ZONE("qq zone"), // qq 空间
    QQ("qq"), // qq
    WEIXIN_CIRCLE("weixin friend circle"), // 微信朋友圈
    WEIXIN_FAVORITE("weixin favorite"), // 微信收藏
    WEIXIN("weixin"), // 微信
    WEIBO("sina weibo"), // 新浪微博
    OTHER("other"), // 其他系统分享
    COPY("copy"); // 复制链接

    private String type;

    ShareType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "type:" + this.type;
    }
}
