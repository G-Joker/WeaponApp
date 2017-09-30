package com.app.server.controller;

import com.app.server.mapper.TaobaoMappper;
import com.app.server.mapper.UserMappper;
import com.app.server.model.UserModel;
import com.app.server.model.response.BaseResponse;
import com.app.server.model.response.ConstResponse;
import com.app.server.model.response.DataResponse;
import com.app.server.model.response.RegisterResponse;
import com.app.server.util.PatternUtil;
import com.app.server.util.SafeUtil;
import com.app.server.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaobaoController {

    @Autowired
    private TaobaoMappper taobaoMappper;

    @RequestMapping("/home/taobaolist")
    public BaseResponse taobao(){
        return new DataResponse(taobaoMappper.selectAll(),ConstResponse.DESC_OK, ConstResponse.STATUS_OK);
    }

}