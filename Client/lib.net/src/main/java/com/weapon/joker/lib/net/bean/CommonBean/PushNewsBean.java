package com.weapon.joker.lib.net.bean.CommonBean;


import com.weapon.joker.lib.net.model.BaseBean;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.net.bean.PushNewsBean
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/17
 *     desc   : 推送消息 bean
 * </pre>
 */

public class PushNewsBean extends BaseBean{
    /**
     * 消息 ID
     */
    public String messageId;
    /**
     * 消息时间
     */
    public String time;
    /**
     * 推送标题
     */
    public String title;
    /**
     * 推送内容
     */
    public String content;
    /**
     * 推送跳转的链接 url
     */
    public String url;
    /**
     * 图片 Url
     */
    public String imageUrl;
}
