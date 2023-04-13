package com.springboot1.service.db;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot1.model.roles;
import com.springboot1.model.users;
import com.springboot1.repository.UserRepository;
import com.springboot1.service.IUsersService;

@Service
public class UserService implements IUsersService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void UpdateCourses(users user) {
        userRepository.save(user);  
    }

    @Override
    public void saveUser(users user){
        userRepository.save(user);
    }

    @Override
    public users findByIdUsers(int idUser){
        Optional<users> optional = userRepository.findById(idUser);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Page<users> getUsersPaginated(Pageable page){
        return userRepository.findAll(page);
    }

    @Override
    public List<users> getUsersByRole(roles role){
        return userRepository.findByRole(role);
    }

    @Override
    public Integer getNumberOfCategories(){
        return userRepository.findNumberOfCategories();
    }

    @Override
    public Integer getNumberOfCourses(){
        return userRepository.findNumberOfCourses();
    }

    @Override
    public Integer getNumberOfUsers(){
        return userRepository.findNumberOfUsers();
    }

    @Override
    public Integer getNumberOfTeachers(){
        return userRepository.findNumberOfTeachers();
    }

    @Override
    public Integer getNumberOfDisbaledUsers(){
        return userRepository.findNumberOfDisbaledUsers();
    }

    @Override
    public String getMostPopularCourse(){
        return userRepository.findMostPopularCourse();
    }
}
