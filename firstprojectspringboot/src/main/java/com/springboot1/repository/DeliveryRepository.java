package com.springboot1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot1.model.activity;
import com.springboot1.model.delivery;
import com.springboot1.model.users;

public interface DeliveryRepository extends JpaRepository<delivery, Integer>{
    
    delivery findByUserAndActivity(users user, activity activity);
    List<delivery> findByActivity(activity activity);

    // Pending activities for students
    List<delivery> findByPendingAndUser(int pending, users user);

    // Verify if the grade exists, if not, the teacher must verify the activity
    List<delivery> findByGradeAndActivity(int grade, activity activity);
}
