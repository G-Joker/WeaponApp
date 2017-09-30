package com.weapon.joker.app.message;

import android.util.Log;

/**
 * MessageModel 消息页面 Model
 * author:张冠之
 * time: 2017/9/30 下午2:38
 * e-mail: guanzhi.zhang@sojex.cn
 */

public class MessageModel {
    private int status;

    private content content;
    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    /** 定义 输出返回数据 的方法 */
    public void show() {
        Log.i("Request",status+"\n"+content.from+"\n"+content.vendor+"\n"+content.out+"\n"+content.errNo);
        System.out.println(status);

        System.out.println(content.from);
        System.out.println(content.to);
        System.out.println(content.vendor);
        System.out.println(content.out);
        System.out.println(content.errNo);
    }
}
