package com.cy.mapper;


import com.cy.entities.Manager;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ManagerLoginMapper {

    @Select("select * from manager where name = #{name} and pwd = #{pwd}")
    Manager checkManagers(@Param("name") String username, @Param("pwd")String password );
}
