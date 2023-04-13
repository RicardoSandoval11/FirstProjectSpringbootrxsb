package com.springboot1.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="courses", schema="schoolmanagementdb")
public class courses {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id_course;
    private String name;
    private Date start_date;
    private Date end_date;
    private Double grade;
    private String cover_image;
    private String description;
    private Integer disabled = 1;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "courses")
    private List<users> users;

    @OneToOne
    @JoinColumn(name="teacher")
    private users teacher;

    @OneToOne
    @JoinColumn(name = "category")
    private category category;

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

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
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

    public List<users> getUsers() {
        return users;
    }

    public void setUsers(List<users> users) {
        this.users = users;
    }

    public users getTeacher() {
        return teacher;
    }

    public void setTeacher(users teacher) {
        this.teacher = teacher;
    }

    public category getCategory() {
        return category;
    }

    public void setCategory(category category) {
        this.category = category;
    }
    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "courses [id_course=" + id_course + ", name=" + name + ", start_date=" + start_date + ", end_date="
                + end_date + ", grade=" + grade + ", cover_image=" + cover_image + ", description=" + description
                + ", users=" + users + ", teacher=" + teacher + ", category=" + category + "]";
    }
    
}
