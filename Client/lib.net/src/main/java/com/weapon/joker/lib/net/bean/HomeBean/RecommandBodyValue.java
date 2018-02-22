package com.weapon.joker.lib.net.bean.HomeBean;

import com.weapon.joker.lib.net.model.BaseBean;

import java.util.ArrayList;

public class RecommandBodyValue extends BaseBean {

    public int type;
    public String logo;
    public String title;
    public String info;
    public String price;
    public String text;
    public String site;
    public String from;
    public String zan;
    public ArrayList<String> url;

    //视频专用
    public String thumb;
    public String resource;
    public String resourceID;
    public String adid;
    public ArrayList<RecommandMonitorModel> startMonitor;
    public ArrayList<RecommandMonitorModel> middleMonitor;
    public ArrayList<RecommandMonitorModel> endMonitor;
    public String clickUrl;
    public ArrayList<RecommandMonitorModel> clickMonitor;
//    public EMEvent event;

}
