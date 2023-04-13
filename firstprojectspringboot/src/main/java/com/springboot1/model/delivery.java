package com.springboot1.model;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "deliveries", schema = "schoolmanagementdb")
public class delivery {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id_delivery;

    @OneToOne
    @JoinColumn(name = "id_activity")
    private activity activity;

    @OneToOne
    @JoinColumn(name = "id_user")
    private users user;

    private String work;

    private Integer pending;

    public Date date;

    public Integer grade;

    public String comments;

    public Integer getId_delivery() {
        return id_delivery;
    }

    public void setId_delivery(Integer id_delivery) {
        this.id_delivery = id_delivery;
    }

    public activity getActivity() {
        return activity;
    }

    public void setActivity(activity activity) {
        this.activity = activity;
    }

    public users getUser() {
        return user;
    }

    public void setUser(users user) {
        this.user = user;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "delivery [id_delivery=" + id_delivery + ", activity=" + activity + ", user=" + user + ", work=" + work
                + ", pending=" + pending + ", date=" + date + ", grade=" + grade + ", comments=" + comments + "]";
    }

}
