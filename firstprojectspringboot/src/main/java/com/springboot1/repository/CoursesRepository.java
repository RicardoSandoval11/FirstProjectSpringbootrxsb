package com.springboot1.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot1.customObjects.customCourseDTO;
import com.springboot1.customObjects.pendingActivitiesByStudent;
import com.springboot1.model.courses;
import com.springboot1.model.users;


public interface CoursesRepository extends JpaRepository<courses, Integer> {
    
    List<courses> findByusers(users user);

    @Query("SELECT new com.springboot1.customObjects.customCourseDTO(c.id_course, c.name, c.start_date, c.end_date, c.cover_image, c.description) FROM courses c WHERE c.category.id_category = :categoryId AND CURDATE() < c.start_date")
    Page<customCourseDTO> findByCategory(@Param("categoryId") int categoryId, Pageable pageable);
    
    List<courses> findByTeacher(users user);

    @Query("SELECT new com.springboot1.customObjects.customCourseDTO(c.id_course, c.name, c.start_date, c.end_date, c.cover_image, c.description) FROM courses c")
    Page<customCourseDTO> findAllCourses(Pageable page);

    @Query("SELECT c.name, a.name, count(d.activity) FROM courses c JOIN activity a ON a.course = c.id_course JOIN delivery d ON d.activity = a.id_activity WHERE d.grade IS NULL AND d.work IS NOT NULL AND c.teacher = :teacher GROUP BY a.id_activity")
    List<Object[]> findPendingReviesByCourse(@Param("teacher") users teacher);

    @Query("SELECT new com.springboot1.customObjects.pendingActivitiesByStudent(a.id_activity, a.name, a.start_date, a.end_date, c.name) FROM activity a JOIN courses c ON a.course = c.id_course JOIN delivery d ON d.activity = a.id_activity WHERE d.work IS NULL AND d.user = :student")
    Page<pendingActivitiesByStudent> findAllPendingActivities(@Param("student") users user,Pageable pageable);
}
