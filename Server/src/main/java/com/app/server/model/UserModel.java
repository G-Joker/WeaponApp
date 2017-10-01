package com.app.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "password" })
public class UserModel {
    public String user;
    public String token;
    public String password;
    public String uid;
}
