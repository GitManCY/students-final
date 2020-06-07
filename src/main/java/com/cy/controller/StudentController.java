package com.cy.controller;


import com.cy.entities.Manager;
import com.cy.mapper.ManagerLoginMapper;
import com.cy.mapper.StudentMapper;
import com.cy.entities.Student;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Controller
public class StudentController {

    @Autowired
    ManagerLoginMapper managerMapper;
    @Autowired
    StudentMapper studentMapper;


    //查询
    @GetMapping("/query")
    public String query(
            @RequestParam("id") String id, @RequestParam("lastName") String lastName,
            Model model) {

        if (lastName.equals("")) {
            lastName = null;
        } ;
        List<Student> students = studentMapper.query(id, lastName);
        model.addAttribute("lists", students);
        return "emp/query";
    }

    //管理员的学生项目列表页面
    @GetMapping("/students")
    public String list(Model model, @RequestParam(value = "pn", defaultValue = "1") Integer pn) {
        PageHelper.startPage(pn, 10);
        List<Student> students = studentMapper.getAll();
        PageInfo page = new PageInfo(students, 10);
        model.addAttribute("pageInfo", page);
        return "emp/list";
    }

    //添加
    @PostMapping("/student")
    public String add(Student student) {

        studentMapper.save(student);
        return "redirect:students";
    }

    //管理员退出登陆
    @GetMapping("/signout")
    public String signout(HttpSession session) {
        session.invalidate();
        return "redirect:visitor";
    }

    //去添加页面
    @GetMapping("/student")
    public String toAddPage(Model model) {
        //查出所有班级
        Set<String> sets = studentMapper.getClasses();
        model.addAttribute("sets", sets);
        return "emp/add";
    }

    //修改页面
    @GetMapping("/student/{id}")
    public String toEditPage(@PathVariable("id") String id, Model model) {
        /*
            查班级
         */
        Set<String> sets = studentMapper.getClasses();
        model.addAttribute("sets", sets);


        Student student = studentMapper.getById(id);
        model.addAttribute("student", student);
        return "emp/add";
    }

    //修改
    @PutMapping("/student")
    public String Edit(Student student) {
        studentMapper.edit(student);
        return "redirect:/students";
    }

    //删除
    @DeleteMapping("/student/{id}")
    public String delete(@PathVariable("id") String id) {
        studentMapper.delete(id);
        return "redirect:/students";
    }

    //管理员登陆
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String, Object> map,
                        HttpSession session) {

        String msg = "用户名或密码错误";
        Manager manager = managerMapper.checkManagers(username, password);
        //重新将数据库的内容读取出来 校验密码的大小写
        if (manager!=null&&manager.getName().equals(username) && manager.getPwd().equals(password)) {
            //登陆成功 为防止表单重复提交 可以重定向到主页
            session.setAttribute("loginUser", username);
            return "redirect:/students";
        } else {
            map.put("msg", msg);
            return "login";
        }
    }


    @ResponseBody
    @GetMapping("/student/checkAdd")
    public int checkAdd(@RequestParam("id") String id) {
        int result = studentMapper.checkAdd(id);
        return result;
    }
}
