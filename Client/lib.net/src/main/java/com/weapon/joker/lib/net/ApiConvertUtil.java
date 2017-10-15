package com.weapon.joker.lib.net;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author : yueyang
 * date : 2017/10/15
 * email : hi.yangyue1993@gmail.com
 */
public class ApiConvertUtil {

    /**
     *
     *  bean转换成map
     * author: yueyang
     **/
    public static <T> Map<String, Object> beanToMap(T bean) {
        try {
            Class<? extends Object> clz = bean.getClass();
            Field[] fields = clz.getFields();
            Map<String, Object> map = new HashMap<>();
            for (Field field : fields) {
                Object value = field.get(bean);
                Class<?> class_ = field.getType();

                if (value == null)
                    continue;
                else if (class_ == List.class) {
                    ArrayList<T> list = (ArrayList<T>) value;
                    if (list.size() == 0)
                        continue;
                } else if (value.getClass() == Long.class) {
                    Long longValue = (Long) value;
                    if (field.getName().equals("serialVersionUID") || longValue == 0)
                        continue;
                } else if (value.getClass() == Double.class) {
                    Double doubleValue = (Double) value;
                    if (doubleValue == 0d)
                        continue;
                } else if (value.getClass() == String.class) {
                    String string = (String) value;
                    if (TextUtils.isEmpty(string))
                        continue;
                }
                map.put(field.getName(), value);
            }

            return map;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}