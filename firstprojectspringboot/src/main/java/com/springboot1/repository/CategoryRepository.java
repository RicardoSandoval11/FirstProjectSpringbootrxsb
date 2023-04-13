package com.springboot1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot1.model.category;

public interface CategoryRepository extends JpaRepository<category, Integer> {
    
}
