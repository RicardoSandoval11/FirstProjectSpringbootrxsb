package com.springboot1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="category",schema = "schoolmanagementdb")
public class category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_category;
    private String name;
    private String description;
    private String cover_image;
    
    public Integer getId_category() {
        return id_category;
    }
    public void setId_category(Integer id_category) {
        this.id_category = id_category;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    @Override
    public String toString() {
        return "category [id_category=" + id_category + ", name=" + name + ", description=" + description
                + ", cover_image=" + cover_image + "]";
    }

}
