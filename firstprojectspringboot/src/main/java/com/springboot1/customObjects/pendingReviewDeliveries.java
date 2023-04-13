package com.springboot1.customObjects;

import org.springframework.beans.factory.annotation.Qualifier;

public class pendingReviewDeliveries {

    private String courseName;
    private String activityName;
    private Long pendingDeliveries;
    
    public pendingReviewDeliveries(
            @Qualifier("name") String courseName, 
            @Qualifier("activity") String activityName,
            @Qualifier("pending") Long pendingDeliveries){
        super();
        this.courseName = courseName;
        this.activityName = activityName;
        this.pendingDeliveries = pendingDeliveries;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Long getPendingDeliveries() {
        return pendingDeliveries;
    }

    public void setPendingDeliveries(Long pendingDeliveries) {
        this.pendingDeliveries = pendingDeliveries;
    }

    @Override
    public String toString() {
        return "pendingReviewDeliveries [courseName=" + courseName + ", activityName=" + activityName
                + ", pendingDeliveries=" + pendingDeliveries + "]";
    }

}
