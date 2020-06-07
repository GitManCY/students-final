package com.cy.controller;

import com.cy.entities.Student;
import com.cy.mapper.StudentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author a123
 * @title: VisitorController
 * @projectName students-final
 * @description: TODO
 * @date 2019/5/224:21 PM
 */
@Controller
public class VisitorController {

    @Autowired
    StudentMapper studentMapper;

    @GetMapping("/visitor")
    public String youke(Model model, @RequestParam(value = "pn",defaultValue = "1") Integer pn) {
        PageHelper.startPage(pn, 10);
        List<Student> students = studentMapper.getAll();
        PageInfo page = new PageInfo(students, 10);
        model.addAttribute("pageInfo", page);
        return "visitor/visitor";
    }

    @GetMapping("/visitorquery")
    public String query(@RequestParam("id") String id, @RequestParam("lastName") String lastName,
            Model model) {
        if (lastName.equals("")) {
            lastName = null;
        } ;
        List<Student> students = studentMapper.query(id,lastName);
        model.addAttribute("vqlist", students);
        return "visitor/visitorquery";
    }

    @GetMapping("/tologin")
    public String toLogin(){
        return "login";
    }
}
