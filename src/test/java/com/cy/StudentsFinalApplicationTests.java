package com.cy;

import com.cy.entities.Student;
import com.cy.mapper.StudentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentsFinalApplicationTests {

    @Autowired
    StudentMapper studentMapper;
    @Test
    public void contextLoads() {
        List<Student> list = studentMapper.getAll();
        for (Student s:list
             ) {
            System.out.println(s);
        }
    }

}
