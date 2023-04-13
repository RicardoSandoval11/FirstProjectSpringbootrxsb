package com.springboot1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springboot1.service.IUsersService;



@Controller
public class AdminController {

    @Autowired
    private IUsersService usersService;
    
    @GetMapping("/admin-panel")
    public String ShowAdminPanel(Model model){
        
        Integer numberCategories = usersService.getNumberOfCategories();
        Integer numberCourses = usersService.getNumberOfCourses();
        Integer numberDisabledUsers = usersService.getNumberOfDisbaledUsers();
        Integer numberTeachers = usersService.getNumberOfTeachers();
        Integer numberUsers = usersService.getNumberOfUsers();
        String popularCourse = usersService.getMostPopularCourse();

        model.addAttribute("numberCategories", numberCategories);
        model.addAttribute("numberCourses", numberCourses);
        model.addAttribute("numberDisabledUsers", numberDisabledUsers);
        model.addAttribute("numberTeachers", numberTeachers);
        model.addAttribute("numberUsers", numberUsers);
        model.addAttribute("popularCourse", popularCourse);
        return "admin/panel";
    }
}
