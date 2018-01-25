package com.app.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    /**
     * 将时间戳转为字符串
     *
     * @param time  时间戳
     * @param style 时间格式 (类似于"yyyy-MM-dd HH:mm")
     * @return
     */
    public static String getStrTime(long time, String style) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(style);
        re_StrTime = sdf.format(new Date(time));
        return re_StrTime;
    }
}
