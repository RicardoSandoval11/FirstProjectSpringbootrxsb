package com.springboot1.customObjects;

import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;

public class customCourseDTO {

    private Integer id_course;
    private String name;
    private Date start_date;
    private Date end_date;
    private String cover_image;
    private String description;

    public customCourseDTO(
            @Qualifier("id_course") Integer id_course, 
            @Qualifier("name") String name, 
            @Qualifier("start_date") Date start_date, 
            @Qualifier("end_date") Date end_date, 
            @Qualifier("cover_image") String cover_image, 
            @Qualifier("description") String description){
        super();
        this.id_course = id_course;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.cover_image = cover_image;
        this.description = description;
    }
    
    public Integer getId_course() {
        return id_course;
    }
    public void setId_course(Integer id_course) {
        this.id_course = id_course;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public String getCover_image() {
        return cover_image;
    }
    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "customCourse [id=" + id_course + ", name=" + name + ", start_date=" + start_date + ", end_date=" + end_date
                + ", cover_image=" + cover_image + ", description=" + description + "]";
    }

}
