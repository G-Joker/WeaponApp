package com.weapon.joker.app.stub.share;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.stub.share.IShareAction
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/09/30
 *     desc   : share action
 * </pre>
 */

public interface IShareAction {

    /**
     * @param shareParams  分享参数
     * @param listener     分享的回调
     */
    void doShare(ShareParams shareParams,  IShareListener listener);
}
