package com.springboot1.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot1.model.activity;
import com.springboot1.model.courses;
import com.springboot1.model.users;
import com.springboot1.repository.ActivityRepository;
import com.springboot1.service.IActivityService;

@Service
public class ActivityService implements IActivityService{
    
    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public void saveActivity(activity activity) {
        activityRepository.save(activity);
        
    }

    @Override
    public List<activity> getAllActivitiesByCourse(courses course) {
        
        return activityRepository.findByCourse(course);
    }

    @Override
    public List<activity> getPendingActivities(users user){
        return null;
    }

    @Override
    public activity findActivityById(int idActivity) {
        Optional<activity> optional = activityRepository.findById(idActivity);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public void deleteActivity(int idAcivity) {
        activityRepository.deleteById(idAcivity);
    }

    @Override
    public void updateActivity(activity activity){
        activityRepository.save(activity);
    }
}
