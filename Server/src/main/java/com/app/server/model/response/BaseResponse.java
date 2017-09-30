package com.app.server.model.response;

public class BaseResponse {
    public String desc;
    public int status;

    public BaseResponse(){}

    public BaseResponse(String desc, int status) {
        this.desc = desc;
        this.status = status;
    }
}
