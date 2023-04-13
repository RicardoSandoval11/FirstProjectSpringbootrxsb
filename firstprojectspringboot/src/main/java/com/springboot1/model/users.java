package com.springboot1.model;



import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="users",schema="schoolmanagementdb")
public class users {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id_user;
    private String username;
    private String password;
    private Integer enabled = 1;
    @OneToOne
    @JoinColumn(name="role")
    private roles role;
    private String name;
    private String email;
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name="users_courses",
        joinColumns= @JoinColumn(name="id_user"),
        inverseJoinColumns= @JoinColumn(name="id_course")
    )
    private List<courses> courses;
    
    public Integer getId_user() {
        return id_user;
    }
    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getEnabled() {
        return enabled;
    }
    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
    public roles getRole() {
        return role;
    }
    public void setRole(roles role) {
        this.role = role;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<courses> getCourses() {
        return courses;
    }
    public void setCourses(List<courses> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "users [id_user=" + id_user + ", username=" + username + ", password=" + password + ", enabled="
                + enabled + ", role=" + role + ", name=" + name + ", email=" + email + ", courses=" + courses + "]";
    }

}
