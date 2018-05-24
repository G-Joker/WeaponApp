package com.app.server.timer;

import com.app.server.mapper.TaobaoMappper;
import com.app.server.model.TaobaoModel;
import com.app.server.request.RestTemplateController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class HomeTimer {

    @Autowired
    private TaobaoMappper taobaoMappper;

    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /*每100秒执行一次*/
    @Scheduled(initialDelay = 1,fixedRate = 60000)
    public void timerRate() {
        String url="https://s.2.taobao.com/list/list.htm";
        List<TaobaoModel> list=RestTemplateController.getInstance().getTaobao(url);
        if (list != null && list.size() > 0) {
            taobaoMappper.deleteAll();
            taobaoMappper.insertAll(list);
        }
    }

    /*每天12:00:00时执行*/
    @Scheduled(cron = "0 00 12 * * ? ")
    public void timerCron() {
        System.out.println("current time : "+ dateFormat.format(new Date()));
    }
}
