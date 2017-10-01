package com.app.server.util;

import java.util.regex.Pattern;

/**
 * Created by foxleezh on 2017/3/14.
 */

public class PatternUtil {

    public static boolean isPassword(String input){
        String pattern = "^\\S{6,18}$";
        return Pattern.matches(pattern, input);
    }

    public static boolean isPhone(String input){
        String pattern = "^1\\d{10}$";
        return Pattern.matches(pattern, input);
    }

    public static String isNickname(String input){
        if(TextUtils.isEmpty(input)){
            return "用户名不能为空";
        }
        if(input.contains("<")||input.contains(">")||input.contains("\\")){
            return "用户名不能包含<>\\字符";
        }
        if(input.length()<1||input.length()>12){
            return "用户名请输入1~12个字符";
        }
        if(TextUtils.isEmpty(input.trim())){
            return "用户名不能只包含空字符";
        }
        return "";
    }

}
