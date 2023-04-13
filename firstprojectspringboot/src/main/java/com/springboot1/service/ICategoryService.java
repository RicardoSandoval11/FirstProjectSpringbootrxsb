package com.springboot1.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot1.model.category;

public interface ICategoryService {
    
    Page<category> getAllCategories(Pageable page);
    category getCategoryById(int idCategory);
    List<category> getAllCategories();
    void saveNewCategory(category category);
    void removeCategory(category category);
}
