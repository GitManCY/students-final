package com.cy.entities;

import lombok.Data;

/**
 * @author a123
 * @title: Manager
 * @projectName students-final
 * @description: TODO
 * @date 2019/5/2410:20 AM
 */
//@Data
public class Manager {

    private String name;
    private String pwd;
    private Integer id;

    public Manager() {
    }

    @Override
    public String toString() {
        return "Manager{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", id=" + id +
                '}';
    }

    public Manager(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
