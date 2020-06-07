package com.cy.mapper;

import com.cy.entities.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

public interface StudentMapper {

    @Select("select * from student")
    List<Student> getAll();

    @Select("select * from student WHERE id = #{id}")
    Student getById(String id);

    @Select({"<script>",
            "SELECT * FROM student",
            "where id = #{id}",
            "<when test='lastName!=null'>",
            "|| lastName like '%${lastName}%' ",
            "</when>",
            "</script>"})
    List<Student> query(@Param("id") String id, @Param("lastName") String lastName);

    @Update("update student set lastName=#{lastName},gender=#{gender},project=#{project},classes=#{classes},projectName=#{projectName} where id=#{id}")
    void edit(Student student);

    @Insert("insert into student(id,lastName,gender,project,classes,projectName) values(#{id},#{lastName},#{gender},#{project},#{classes},#{projectName})")
    void save(Student student);

    @Delete("delete from student where id=#{id}")
    void delete(String id);

    @Select("select classes from student")
    Set<String> getClasses();

    @Select("select count(*) from student where id = #{id}")
    int checkAdd(String id);
}
