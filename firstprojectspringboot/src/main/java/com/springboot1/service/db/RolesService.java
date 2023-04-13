package com.springboot1.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot1.model.roles;
import com.springboot1.repository.RolesRepository;
import com.springboot1.service.IRolesService;

@Service
public class RolesService implements IRolesService{
    
    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public roles getRoleById(int roleId){
        Optional<roles> optional = rolesRepository.findById(roleId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public  List<roles> getAllRoles(){
        return rolesRepository.findAll();
    }
}
