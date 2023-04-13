package com.springboot1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot1.model.activity;
import com.springboot1.model.courses;


public interface ActivityRepository extends JpaRepository<activity, Integer> {
    
    List<activity> findByCourse(courses course);

}
