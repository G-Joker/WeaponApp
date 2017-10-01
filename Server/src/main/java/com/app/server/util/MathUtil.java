package com.app.server.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by foxlee on 2016/3/5.
 */
public class MathUtil {

    public static int getInt(String v){
        if(!TextUtils.isEmpty(v)){
            try {
                v = v.replace(" ", "");
                v = v.replace("+", "");
                v = v.replace("%", "");
                v = v.replace(",", "");
                int d = Integer.parseInt(v);
                return d;
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    public static long getLong(String v){
        if(!TextUtils.isEmpty(v)){
            try {
                v = v.replace(" ", "");
                v = v.replace("+", "");
                v = v.replace("%", "");
                v = v.replace(",", "");
                long d = Long.parseLong(v);
                return d;
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    public static double getDouble(String v){
        if(!TextUtils.isEmpty(v)){
            try {
                v = v.replace(" ", "");
                v = v.replace("+", "");
                v = v.replace("%", "");
                v = v.replace(",", "");
                double d = Double.parseDouble(v);
                return d;
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    public static float getFloat(String v){
        if(!TextUtils.isEmpty(v)){
            try {
                v = v.replace(" ", "");
                v = v.replace("+", "");
                v = v.replace("%", "");
                v = v.replace(",", "");
                float d = Float.parseFloat(v);
                return d;
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }


    /**
     * 将数字转换为给定小数位数的字符
     */
    public static String formatDecimal(double number, int digits, boolean divider) {

        StringBuffer a = new StringBuffer();
        for (int i = 0; i < digits; i++) {
            if (i == 0)
                a.append(".");
            a.append("0");
        }
        DecimalFormat nf;
        if (divider) {
            nf = new DecimalFormat("###,###,###,##0" + a.toString());
        } else {
            nf = new DecimalFormat("##0" + a.toString());
        }
        String formatted = nf.format(number);
        return formatted;
    }

    public static String formatDecimal(double number, int digits) {
        return formatDecimal(number, digits, true);
    }

    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    public static String formatDouble(double number) {
        if (Double.isNaN(number) || number == 0) {
            return "--";
        }
        return subZeroAndDot(number + "");
    }


    /**
     *
     * 提供精确的加法运算
     *
     * @param v1
     *
     * @param v2
     *
     * @return 两个参数数学加和，以字符串格式返回
     */

    public static String add(String v1, String v2) {

        try {
            BigDecimal b1 = new BigDecimal(v1);

            BigDecimal b2 = new BigDecimal(v2);

            return b1.add(b2).toString();
        } catch (Exception e) {
            return "0";
        }

    }


    /**
     *
     * 提供精确的减法运算
     *
     * @param v1
     *
     * @param v2
     *
     * @return 两个参数数学差，以字符串格式返回
     */

    public static String subtract(String v1, String v2)

    {

        try {
            BigDecimal b1 = new BigDecimal(v1);

            BigDecimal b2 = new BigDecimal(v2);

            return b1.subtract(b2).toString();
        } catch (Exception e) {
            return "0";
        }

    }

    /**
     *
     * 提供精确的乘法运算
     *
     * @param v1
     *
     * @param v2
     *
     * @return 两个参数的数学积，以字符串格式返回
     */

    public static String multiply(String v1, String v2)

    {

        try {
            BigDecimal b1 = new BigDecimal(v1);

            BigDecimal b2 = new BigDecimal(v2);

            return b1.multiply(b2).toString();
        } catch (Exception e) {
            return "0";
        }

    }


    /**
     *
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     *
     * 小数点以后10位，以后的数字四舍五入,舍入模式采用ROUND_HALF_EVEN
     *
     * @param v1
     *
     * @param v2
     *
     * @return 两个参数的商，以字符串格式返回
     */

    public static String divide(String v1, String v2)

    {

        return divide(v1, v2, 10,BigDecimal.ROUND_HALF_EVEN);

    }

    /**
     *
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     *
     * 定精度，以后的数字四舍五入。舍入模式采用用户指定舍入模式
     *
     * @param v1
     *
     * @param v2
     *
     * @param scale
     *            表示需要精确到小数点以后几位
     *
     * @param round_mode
     *            表示用户指定的舍入模式
     *
     * @return 两个参数的商，以字符串格式返回
     */

    public static String divide(String v1, String v2, int scale, int round_mode)

    {

        try {
            if (scale < 0)

            {

                throw new IllegalArgumentException(
                        "The scale must be a positive integer or zero");

            }

            BigDecimal b1 = new BigDecimal(v1);

            BigDecimal b2 = new BigDecimal(v2);

            return b1.divide(b2, scale, round_mode).toString();
        } catch (Exception e) {
            return "0";
        }

    }
}
