package com.springboot1.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot1.model.category;
import com.springboot1.repository.CategoryRepository;
import com.springboot1.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<category> getAllCategories(Pageable page) {
        return categoryRepository.findAll(page);
    }
    
    @Override
    public category getCategoryById(int idCategory){
        Optional<category> optional = categoryRepository.findById(idCategory);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
    
    @Override
    public List<category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @Override
    public void saveNewCategory(category category){
        categoryRepository.save(category);
    }

    @Override
    public void removeCategory(category category){
        categoryRepository.delete(category);
    }
}
