package com.app.server.model.response;

public class DataResponse extends BaseResponse{
    public Object data;

    public DataResponse(){}

    public DataResponse(Object data, String desc, int status) {
        super(desc,status);
        this.data = data;
    }
}
