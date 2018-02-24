package com.weapon.joker.lib.video.okhttp;

/**
 * @author: qndroid
 * @function: all url in the sdk
 * @date: 16/6/1
 */
public class HttpConstant {

    //代表客户端版本号
    public static final String AVS = "avs";
    //代表本次请求的会话，从reqid中取值
    public static final String SID = "sid";
    //代表本次请求的返回的素材id
    public static final String IE = "ie";
    //code代表当前step的结果码
    public static final String STEP_CD = "cd";

    //广告数据返回成功
    public static final String AD_DATA_SUCCESS = "200";
    //广告数据解析失败
    public static final String AD_DATA_FAILED = "202";
    //广告加载成功
    public static final String AD_PLAY_SUCCESS = "300";
    //广告加载失败
    public static final String AD_PLAY_FAILED = "301";

    /**
     * 埋点请求相关参数常量
     */
    public enum Params {

        lvs("lvs", "4"), st("st", "12"), bt_phone("bt", "1"), bt_pad("bt", "0"),
        os("os", "1"), p("p", "2"), appid("appid", "xya"),
        ad_analize("sp", "2"),
        ad_load("sp", "3");

        private String value;
        private String key;

        private Params(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getKey() {
            return key;
        }
    }

    //播放器&移动后台监控埋点日志地址
    public static final String ATM_MONITOR = "http://count.atm.youku.com/mlog";

    public static final String ATM_PRE = "val.atm.youku.com";

    /**
     * 广告服务器地址,待定
     */
    public static final String DISPLAY_AD_URL = "http://api.youku.com/image.php";
}
