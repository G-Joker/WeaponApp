package com.app.server.controller;

import com.app.server.mapper.UserMappper;
import com.app.server.model.response.BaseResponse;
import com.app.server.model.response.DataResponse;
import com.app.server.model.response.ConstResponse;
import com.app.server.model.UserModel;
import com.app.server.model.response.RegisterResponse;
import com.app.server.util.PatternUtil;
import com.app.server.util.SafeUtil;
import com.app.server.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMappper userMappper;

    @RequestMapping("/user/login")
    public BaseResponse login(String name, String password){
        System.out.println("phone="+name+" password="+password);
        if(name==null||password==null){
            return new BaseResponse(ConstResponse.DESC_PARAM, ConstResponse.STATUS_PARAM_ERROR);
        }
        UserModel userModel= userMappper.findUserByname(name);
        if(userModel==null){
            return new BaseResponse("用户名不存在", ConstResponse.STATUS_KNOWN_ERROR);
        }
        password = SafeUtil.shortMD5(password);
        if(!userModel.password.equals(password)){
            return new BaseResponse("用户名或密码错误", ConstResponse.STATUS_KNOWN_ERROR);
        }
        RegisterResponse registerResponse = userMappper.findRegisterByname(name);
        return new DataResponse(registerResponse,"登录成功", ConstResponse.STATUS_OK);
    }

    @RequestMapping("/user/register")
    public BaseResponse register(String name, String password){
        System.out.println("phone="+name+" password="+password);
        if(name==null||password==null){
            return new BaseResponse(ConstResponse.DESC_PARAM, ConstResponse.STATUS_PARAM_ERROR);
        }

        String nameCheck=PatternUtil.isNickname(name);
        if (!Util.isEmpty(nameCheck)) {
            return new BaseResponse(nameCheck, ConstResponse.STATUS_KNOWN_ERROR);
        }
        if (!PatternUtil.isPassword(password)) {
            return new BaseResponse("请输入6~18位密码", ConstResponse.STATUS_KNOWN_ERROR);
        }
        UserModel userfind = userMappper.findUserByname(name);
        if (userfind != null) {
            return new BaseResponse("用户已注册", ConstResponse.STATUS_KNOWN_ERROR);
        }
        String token = SafeUtil.MD5(name + "app" + password);
        password = SafeUtil.shortMD5(password);
        userMappper.register(name, password, token);
        RegisterResponse registerResponse = userMappper.findRegisterByname(name);
        return new DataResponse(registerResponse, ConstResponse.DESC_OK,ConstResponse.STATUS_OK);
    }


}