package com.app.server.mapper;

import com.app.server.model.UserModel;
import com.app.server.model.response.RegisterResponse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMappper {


    @Select("select * from user where user = #{user}")
    public UserModel findUserByname(String user);

    @Select("select * from user where user = #{user}")
    public RegisterResponse findRegisterByname(String user);


    @Insert("insert into user(user, password,token) values(#{phone}, #{password}, #{token})")
    public void register(@Param("phone")String phone, @Param("password")String password, @Param("token")String token);


//    @Select("select * from user where id = #{id}")
//    public UserModel getById(long id);
//
//
//    @Select("select name from user where id = #{id}")
//    public String getNameById(long id);


}
