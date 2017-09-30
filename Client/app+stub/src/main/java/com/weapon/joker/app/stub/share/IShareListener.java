package com.weapon.joker.app.stub.share;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.stub.share.IShareListener
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/09/29
 *     desc   : 分享接口
 * </pre>
 */

public interface IShareListener {
    void onShareSuccess(ShareType shareType, String desc);
    void onShareFailed(ShareType shareType, String  errorMessage);
    void onShareCancel(ShareType shareType);
}
