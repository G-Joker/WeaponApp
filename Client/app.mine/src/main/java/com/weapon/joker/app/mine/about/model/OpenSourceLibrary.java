package com.weapon.joker.app.mine.about.model;

import java.io.Serializable;

/**
 * author : yueyang
 * date : 2017/11/4
 * e-mail: hi.yangyue1993@gmail.com
 */
public class OpenSourceLibrary implements Serializable {

    private String name;
    private String link;
    private String licenses;

    public String getLicenses() {
        return licenses;
    }

    public void setLicenses(String licenses) {
        this.licenses = licenses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
