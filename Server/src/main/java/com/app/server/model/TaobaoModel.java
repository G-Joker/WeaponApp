package com.app.server.model;

import com.app.server.util.SafeUtil;
import com.app.server.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "sign" })
public class TaobaoModel {
    public String user;
    public String title;
    public String image;
    public double price;
    public String time;
    public String region;
    public String sign;

    public String getSign(){
        return SafeUtil.MD5(user+title+image+price+time+region);
    }

    @Override
    public String toString() {
        return "TaobaoModel{" +
                "user='" + user + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", price='" + price + '\'' +
                ", time='" + time + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
