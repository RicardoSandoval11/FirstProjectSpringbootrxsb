package com.springboot1.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.springboot1.customObjects.customCourseDTO;
import com.springboot1.customObjects.pendingActivitiesByStudent;
import com.springboot1.model.courses;
import com.springboot1.model.users;



public interface ICoursesService {
    
   List<courses> courseByUser(users user);
   courses getCourseById(int idCourse);
   Page<customCourseDTO> getCoursesByCategory(int categoryId, Pageable page);
   List<courses> getCoursesByTeacher(users user);
   void saveCourse(courses course);
   Page<customCourseDTO> getAllCourses(Pageable page);
   List<Object[]> getPendingReviewsForTeacher(users teacher);
   Page<pendingActivitiesByStudent> getAllPendingActivities(users user, Pageable pageable);
   void deleteCourse(courses course);
}
