package com.cy.mapper;

import com.cy.entities.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface FileMapper {
    //导入Excel
    @Insert({
            "INSERT INTO student(id,lastName,gender,projectName,project,classes) VALUES (#{id}, #{lastName}, #{gender},#{projectName},#{project},#{classes})",
            "ON DUPLICATE KEY UPDATE lastName = #{lastName},",
            "gender = #{gender},",
            "projectName = #{projectName},",
            "project = #{project},",
            "classes = #{classes}"
    })
    int insert(
            @Param("id") String id,
            @Param("lastName") String lastName,
            @Param("gender") Integer gender,
            @Param("projectName") String projectName,
            @Param("project") String project,
            @Param("classes") String classes);

    @Select("select * from student order by classes asc,id asc")
    List<Student> expoetExcel();
}

