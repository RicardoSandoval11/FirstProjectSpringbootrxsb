package com.springboot1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="roles", schema ="schoolmanagementdb")
public class roles {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id_role;
    private String role_name;

    public Integer getId_role(){
        return id_role;
    }

    public void setId_role(Integer id_role){
        this.id_role = id_role;
    }

    public String getRole_name(){
        return role_name;
    }

    public void setRole_name(String role_name){
        this.role_name = role_name;
    }

    @Override
    public String toString(){
        return "Role [id="+ id_role +" role = "+role_name+"]";
    }
}
