package com.springboot1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot1.model.roles;
import com.springboot1.model.users;


public interface UserRepository extends JpaRepository<users, Integer>{

    users findByUsername(String username);

    List<users> findByRole(roles role);

    @Query("SELECT COUNT(c.id_category) FROM category c")
    Integer findNumberOfCategories();

    @Query("SELECT COUNT(c.id_course) FROM courses c")
    Integer findNumberOfCourses();

    @Query("SELECT COUNT(u.id_user) FROM users u")
    Integer findNumberOfUsers();

    @Query("SELECT COUNT(u.id_user) FROM users u WHERE u.role = 2")
    Integer findNumberOfTeachers();

    @Query("SELECT COUNT(u.id_user) FROM users u WHERE u.enabled = 0")
    Integer findNumberOfDisbaledUsers();

    @Query("SELECT c.name FROM courses c WHERE c.id_course = (SELECT id_course FROM courses GROUP BY id_course ORDER BY COUNT(id_course) DESC LIMIT 1 )")
    String findMostPopularCourse();

}
