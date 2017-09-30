package com.app.server.mapper;

import com.app.server.model.TaobaoModel;
import com.app.server.model.UserModel;
import com.app.server.request.RestProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

public interface TaobaoMappper {

    @InsertProvider(type = RestProvider.class, method = "insertAllTaobao")
    void insertAll(@Param("list") List<TaobaoModel> users);

    /**
     * 清空所有记录，并重新定义index
     */
    @Select("TRUNCATE TABLE taobao")
    void deleteAll();


    @Select("select * from taobao")
    public ArrayList<TaobaoModel> selectAll();

}
