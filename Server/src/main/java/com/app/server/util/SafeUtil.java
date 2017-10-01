package com.app.server.util;

import java.security.MessageDigest;

/**
 * Created by Administrator on 2015/10/26.
 */
public class SafeUtil {

    public static String MD5(String s) {
        if(s == null||"".equals(s)){
            return "";
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * 缩小的16位md5值
     * @param s
     * @return
     */
    public static String shortMD5(String s) {
        String md5=MD5(s);
        if(!TextUtils.isEmpty(md5)&&md5.length()>23) {
            return md5.substring(8, 24);
        }else {
            return s;
        }
    }

    public static String toHexString(byte[] data){
        StringBuilder sb = new StringBuilder(data.length * (2));

        int pb = 0;
//        String pre = "";
        while(pb < data.length){
            byte b = data[pb++];

            int h = ((0xF0 & b) >> 4);
            int l = (0x0F & b);

//            sb.append(pre);
            if(h < 10){
                sb.append((char)('0' + h));
            }else{
                sb.append((char)('a' + h - 10));
            }
            if(l < 10){
                sb.append((char)('0' + l));
            }else{
                sb.append((char)('a' + l - 10));
            }
//            pre = seperator;
        }

        return sb.toString();
    }

    /**
     * 将十六进制字符串表示的字节数据还原成字节数组
     * @param text 被还原的字符串
     * @return 还原之后的字节数组
     */
    public static byte[] fromHexString(String text){
        if(text == null)
            return new byte[0];

        byte[] result = new byte[text.length() / 2];

        text = text.toLowerCase();
        int pb = 0;
        int ps = 0;
        while(pb < result.length && ps < text.length()){
            char ch = text.charAt(ps++);
            char cl = text.charAt(ps++);

            int a = 0;
            int b = 0;
            if('0' <= ch && ch <= '9'){
                a = ch - '0';
            }else{
                a = ch - 'a' + 10;
            }
            if('0' <= cl && cl <= '9'){
                b = cl - '0';
            }else{
                b = cl - 'a' + 10;
            }

            result[pb++] = (byte)((a << 4) + b);
        }

        return result;
    }


    public static String SafeMD5(String s) {
         return MD5(s).substring(0,16);
    }


    public static int getInt(String v) {
        try {
            v = v.replace(" ", "");
            v = v.replace("+", "");
            v = v.replace("%", "");
            int d = Integer.parseInt(v);
            return d;
        } catch (Exception e) {
            return 0;
        }
    }
}
