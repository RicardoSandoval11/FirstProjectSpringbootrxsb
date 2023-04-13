package com.springboot1.service;

import java.util.List;

import com.springboot1.model.roles;

public interface IRolesService {
    
    roles getRoleById(int roleId);
    List<roles> getAllRoles();
}
