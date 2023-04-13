package com.springboot1.customObjects;

import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;

public class pendingActivitiesByStudent {
    
    private Integer id_activity;
    private String activityName;
    private String courseName;
    private Date start_date;
    private Date end_date;

    public pendingActivitiesByStudent(
        @Qualifier("id_activity") Integer id_activity,
        @Qualifier("activity_name") String activityName,
        @Qualifier("start_date") Date start_date,
        @Qualifier("end_date") Date end_date,
        @Qualifier("name") String courseName
    ) {
        super();
        this.id_activity = id_activity;
        this.activityName = activityName;
        this.courseName = courseName;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Integer getId_activity() {
        return id_activity;
    }

    public void setId_activity(Integer id_activity) {
        this.id_activity = id_activity;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    @Override
    public String toString() {
        return "pendingActivitiesByStudent [id_activity=" + id_activity + ", activityName=" + activityName
                + ", courseName=" + courseName + ", start_date=" + start_date + ", end_date=" + end_date + "]";
    }

}
