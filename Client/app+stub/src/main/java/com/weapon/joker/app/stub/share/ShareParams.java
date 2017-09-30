package com.weapon.joker.app.stub.share;


import java.io.Serializable;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.stub.share.ShareParams
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/09/28
 *     desc   : 分享参数
 * </pre>
 */

public class ShareParams implements Serializable {

    private String title; // 分享标题
    private String description; // 分享描述
    private String appUrl; // 分享 APP 链接
    private String imgUrl; // 分享图片地址
    private int    resId; // 分享图片资源ID

    private ShareType shareType;

    private ShareParams(Builder builder) {
        title = builder.title;
        description = builder.description;
        appUrl = builder.appUrl;
        imgUrl = builder.imgUrl;
        resId = builder.resId;
    }

    public void setShareType(ShareType shareType) {
        this.shareType = shareType;
    }

    public ShareType getShareType() {
        return shareType;
    }

    public static final class Builder {
        private String title;
        private String description;
        private String appUrl;
        private String imgUrl;
        private int    resId;

        public Builder() {
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setAppUrl(String appUrl) {
            this.appUrl = appUrl;
            return this;
        }

        public Builder setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
            return this;
        }

        public Builder setResId(int resId) {
            this.resId = resId;
            return this;
        }

        public ShareParams build() {
            return new ShareParams(this);
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getResId() {
        return resId;
    }

    @Override
    public String toString() {
        return "title:\t" + getTitle() + "\n" +
                "description:\t" + getDescription() + "\n" +
                "appUrl:\t" + getAppUrl() + "\n" +
                "imgUrl:\t" + getImgUrl();
    }
}
