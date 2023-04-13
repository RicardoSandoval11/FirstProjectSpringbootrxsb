package com.springboot1.service;

import java.util.List;

import com.springboot1.model.activity;
import com.springboot1.model.courses;
import com.springboot1.model.users;

public interface IActivityService {

    void saveActivity(activity activity);
    List<activity> getAllActivitiesByCourse(courses course);
    List<activity> getPendingActivities(users user);
    activity findActivityById(int idActivity);
    void deleteActivity(int idAcivity);
    void updateActivity(activity activity);
}
