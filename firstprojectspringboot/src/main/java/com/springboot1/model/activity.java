package com.springboot1.model;

import java.sql.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="activity", schema="schoolmanagementdb")
public class activity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id_activity;
    @OneToOne
    @JoinColumn(name="course")
    private courses course;
    private String description;
    private String name;
    private Integer assigned;
    private String type;
    private Date start_date;
    private Date end_date;
    
    public Integer getId_activity() {
        return id_activity;
    }
    public void setId_activity(Integer id_activity) {
        this.id_activity = id_activity;
    }
    public courses getCourse() {
        return course;
    }
    public void setCourse(courses course) {
        this.course = course;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAssigned() {
        return assigned;
    }
    public void setAssigned(Integer assigned) {
        this.assigned = assigned;
    } 
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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
        return "activity [id_activity=" + id_activity + ", course=" + course + ", description=" + description
                + ", name=" + name + ", assigned=" + assigned + ", type=" + type + ", start_date=" + start_date
                + ", end_date=" + end_date + "]";
    }

}
