package com.app.server.request;

import com.app.server.model.TaobaoModel;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestProvider {

    public String insertAllTaobao(Map map) {
        List<TaobaoModel> list = (List<TaobaoModel>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("REPLACE INTO taobao ");
        sb.append("(user, title, image, price, time, region, sign) ");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].user}, #'{'list[{0}].title}, #'{'list[{0}].image}," +
                " #'{'list[{0}].price}, #'{'list[{0}].time}, #'{'list[{0}].region}, #'{'list[{0}].sign})");
        for (int i = 0; i < list.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
