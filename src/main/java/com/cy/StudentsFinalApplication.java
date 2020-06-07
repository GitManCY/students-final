package com.cy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Properties;


@SpringBootApplication
@MapperScan("com.cy.mapper")
public class StudentsFinalApplication  extends SpringBootServletInitializer{


    public static void main(String[] args) {
        SpringApplication.run(StudentsFinalApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(StudentsFinalApplication.class);
    }

}