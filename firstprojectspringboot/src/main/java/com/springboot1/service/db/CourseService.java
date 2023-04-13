package com.springboot1.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot1.repository.CoursesRepository;
import com.springboot1.service.ICoursesService;



import com.springboot1.customObjects.customCourseDTO;
import com.springboot1.customObjects.pendingActivitiesByStudent;
// import models
import com.springboot1.model.courses;
import com.springboot1.model.users;

@Service
public class CourseService implements ICoursesService {

    @Autowired
    private CoursesRepository coursesRepository;

    @Override
    public List<courses> courseByUser(users user){
        return coursesRepository.findByusers(user);
    }

    @Override
    public courses getCourseById(int idCourse) {
        Optional<courses> optional = coursesRepository.findById(idCourse);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Page<customCourseDTO> getCoursesByCategory(int categoryId, Pageable page){
        return coursesRepository.findByCategory(categoryId, page);
    }

    @Override
    public List<courses> getCoursesByTeacher(users user){
        return coursesRepository.findByTeacher(user);
    }

    @Override
    public void saveCourse(courses course){
        coursesRepository.save(course);
    }

    @Override
    public Page<customCourseDTO> getAllCourses(Pageable page){
        return coursesRepository.findAllCourses(page);
    }

    @Override
    public List<Object[]> getPendingReviewsForTeacher(users teacher){
        return coursesRepository.findPendingReviesByCourse(teacher);
    }

    @Override
    public Page<pendingActivitiesByStudent> getAllPendingActivities(users user, Pageable pageable){
        return coursesRepository.findAllPendingActivities(user, pageable);
    }

    @Override
    public void deleteCourse(courses course){
        coursesRepository.delete(course);
    }

}
