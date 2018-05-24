package com.app.server.request;

import com.app.server.model.TaobaoModel;
import com.app.server.util.MathUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class RestTemplateController {

    private static RestTemplate restTemplate;
    private static RestTemplateController controller;

    private RestTemplateController(){

    }

    public static RestTemplateController getInstance(){
        if(restTemplate==null) {
            restTemplate = new RestTemplate();
            controller = new RestTemplateController();
        }
        return controller;
    }


    public Document getUrlDoc(String url){
        String s= restTemplate.getForObject(url, String.class);
        Document doc = null;
        try {
            doc = Jsoup.parse(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    public List<TaobaoModel> getTaobao(String url){
        Document doc=getUrlDoc(url);
        if(doc==null){
            return new ArrayList<>();
        }
        List<TaobaoModel> list=new ArrayList<>();
        Elements trs = doc.select("div[class=item-block item-idle sh-roundbox]");
        try {
            for (int i=0; i<trs.size(); i++){
                TaobaoModel taobaoModel=new TaobaoModel();
                Element user = trs.get(i).select("div[class=seller-avatar]").get(0);
                taobaoModel.user=user.select("a").attr("title");
                Element info = trs.get(i).select("div[class=item-info]").get(0);
                Elements pic = info.select("div[class=item-pic]");
                Elements img=pic.get(0).select("img");
                taobaoModel.image="http:"+img.attr("data-ks-lazyload-custom");
                Elements attributes = info.select("div[class=item-attributes]");
                taobaoModel.region=attributes.get(0).select("div[class=item-location]").get(0).text();
                taobaoModel.price= MathUtil.getDouble(attributes.get(0).select("em").get(0).text());
                taobaoModel.title=info.select("div[class=item-brief-desc]").get(0).text();
                taobaoModel.time=info.select("span[class=item-pub-time]").get(0).text();
                taobaoModel.sign=taobaoModel.getSign();
                list.add(taobaoModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
