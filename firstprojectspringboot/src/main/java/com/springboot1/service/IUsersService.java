package com.springboot1.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot1.model.roles;
import com.springboot1.model.users;

public interface IUsersService {
    
    users findByUsername(String username);

    void UpdateCourses(users user);

    void saveUser(users user);

    users findByIdUsers(int idUser);
    
    Page<users> getUsersPaginated(Pageable page);

    List<users> getUsersByRole(roles role);

    Integer getNumberOfCategories();

    Integer getNumberOfCourses();

    Integer getNumberOfUsers();

    Integer getNumberOfTeachers();

    Integer getNumberOfDisbaledUsers();

    String getMostPopularCourse();

}
