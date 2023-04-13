package com.springboot1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot1.model.category;
import com.springboot1.service.ICategoryService;
import com.springboot1.util.Util;

@Controller 
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;



    @Value("${coursesapp.path.images}")
    private String coverImagesPath;
    
    @GetMapping("/list-all")
    public String listAllCategories(Pageable page, Model model){
        Page<category> categories = categoryService.getAllCategories(page);
        model.addAttribute("categories", categories);
        return "category/list_all";
    }

    @GetMapping("/create-category")
    public String createCategory(category category, Model model){
        model.addAttribute("category", category);
        return "category/create_category";
    }

    @PostMapping("/save-category")
    public String saveCategory(@ModelAttribute("category") category category, BindingResult results, RedirectAttributes attributes, @RequestParam("coverImage") MultipartFile multipartFile){

        if (results.hasErrors()) {
            return "category/create_category";
        }

        //Save category
        if (!multipartFile.isEmpty()) {
            String fileName = Util.saveFile(multipartFile, coverImagesPath);
            if (fileName != null) {
                category.setCover_image(fileName);
            }
        }

        categoryService.saveNewCategory(category);
        attributes.addFlashAttribute("category_created_msg", "The category "+category.getName()+" has been successfully saved!");

        return "redirect:/categories/list-all";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") int idCategory, Model model){
        category category = categoryService.getCategoryById(idCategory);
        model.addAttribute("category", category);
        return "category/create_category";
    }

    @GetMapping("/remove/{id}")
    public String removeCategoryConfirmation(@PathVariable("id") int idcategory, RedirectAttributes attributes){
        category category = categoryService.getCategoryById(idcategory);
        try {
            categoryService.removeCategory(category);
            attributes.addFlashAttribute("delete_message", "The category "+category.getName()+" has been successfully removed.");
            return "redirect:/categories/list-all";
        } catch (Exception e) {
            attributes.addFlashAttribute("delete_message", "The category "+category.getName()+" cannot be removed because it contains available courses.");
            return "redirect:/categories/list-all";
        }
    }
}

