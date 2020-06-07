package com.cy.entities;

import lombok.Data;

//@Data
public class Student {

    private String id;
    private String lastName;

    private Integer gender;
    private String project;

    private String classes;
    private String projectName;

    public Student() {
    }

    public String getProjectName() {
        return projectName;
    }

    public Student(String id, String lastName, Integer gender, String project, String classes, String projectName) {
        this.id = id;
        this.lastName = lastName;
        this.gender = gender;
        this.project = project;
        this.classes = classes;
        this.projectName = projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }



    public String getProject() {
        return project;
    }


    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", project='" + project + '\'' +
                ", classes='" + classes + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }
}