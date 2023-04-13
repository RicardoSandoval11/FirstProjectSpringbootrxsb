package com.springboot1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot1.customObjects.customCourseDTO;
import com.springboot1.model.category;
import com.springboot1.service.ICategoryService;
import com.springboot1.service.ICoursesService;

@Controller
public class HomeController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ICoursesService coursesService;
    
    @GetMapping("/")
    public String Home(Model model, Pageable page) {
        Page<category> categories = categoryService.getAllCategories(page);
        model.addAttribute("categories", categories);
        return "home/home";
    }

    @GetMapping("/filter/{id}")
    public String filterCourses(
        Model model, 
        @PathVariable("id") int idCategory, 
        @RequestParam(name = "name", required = false) String name,
        @RequestParam(name="page", defaultValue = "0") int page,
        @RequestParam(defaultValue = "6") int size
        ){
            // Creating the pageable object
            Pageable pageable = PageRequest.of(page, size, Sort.by("name"));

            Page<customCourseDTO> coursesFiltered;
            
            coursesFiltered = coursesService.getCoursesByCategory(idCategory, pageable);

            model.addAttribute("totalPages", coursesFiltered.getTotalPages());
            model.addAttribute("currentPage", coursesFiltered.getNumber());
            model.addAttribute("courses", coursesFiltered.getContent());
            model.addAttribute("category_id", idCategory);
            model.addAttribute("search", name);

            return "courses/filter_courses";
        }

}
