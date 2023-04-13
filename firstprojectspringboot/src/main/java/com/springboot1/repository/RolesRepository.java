package com.springboot1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot1.model.roles;

public interface RolesRepository extends JpaRepository<roles, Integer>{
    
    
}
