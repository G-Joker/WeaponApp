package com.app.server.mapper;

import com.app.server.model.UserModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMappper {


    @Select("select * from user where user = #{user}")
    UserModel findUserByname(String user);

    @Insert("insert into user(user, password,token) values(#{phone}, #{password}, #{token})")
    void register(@Param("phone")String phone, @Param("password")String password, @Param("token")String token);


}
