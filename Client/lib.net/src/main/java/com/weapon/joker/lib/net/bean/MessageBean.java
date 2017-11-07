package com.weapon.joker.lib.net.bean;

import com.weapon.joker.lib.middleware.utils.LogUtils;

/**
 * MessageModel 消息页面 Model
 * author:张冠之
 * time: 2017/9/30 下午2:38
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class MessageBean {
    public int status;

    public content content;
    public static class content {
        public String from;
        public String to;
        public String vendor;
        public String out;
        public int errNo;
    }

    /** 定义 输出返回数据 的方法 */
    public void show() {
        LogUtils.i("Request" + status + "\n" + content.from + "\n" + content.vendor + "\n" + content.out + "\n" + content.errNo);
    }
}
