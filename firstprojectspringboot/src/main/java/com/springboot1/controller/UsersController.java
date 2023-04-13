package com.springboot1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot1.customObjects.pendingActivitiesByStudent;
import com.springboot1.customObjects.pendingReviewDeliveries;
import com.springboot1.model.roles;
import com.springboot1.model.users;
import com.springboot1.service.ICoursesService;
import com.springboot1.service.IRolesService;
import com.springboot1.service.IUsersService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsersController {

    @Autowired
    private IUsersService usersService;

    @Autowired
    private IRolesService rolesService;

    @Autowired
    private ICoursesService coursesService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registerUser(@ModelAttribute users user){
        
        return "users/register_form";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute users user, BindingResult result, RedirectAttributes attributes){

        // Return register form with errors
        if(result.hasErrors()){
            for(ObjectError error: result.getAllErrors()){
                System.out.println("Error: "+error.getDefaultMessage());
            }
            return "users/register_form";
        }

        // Encrypt password
        String pwd = user.getPassword();
        String pwd_encp = passwordEncoder.encode(pwd);
        user.setPassword(pwd_encp);

        // Default role as student
        roles defaultRole = new roles();
        defaultRole.setId_role(3);
        defaultRole.setRole_name("student");

        user.setRole(defaultRole);

        // Save user
        usersService.saveUser(user);

        // Redirect user to login page
        attributes.addFlashAttribute("msg", "Start learning now. Start by logging in and assign yourself some courses of your interest!");
        return "redirect:/login";

    }

    @GetMapping("/login")
    public String loginPage(){
        return "users/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

        logoutHandler.logout(request, null, null);

        return "redirect:/login";
    }

    @GetMapping("/all-users")
    public String getUsers(Model model, Pageable page){
        Page<users> users = usersService.getUsersPaginated(page);
        for (users user : users) {
            user.setPassword(null);
        }
        model.addAttribute("users", users);
        return "users/all_users";
    }

    @GetMapping("/edit-permission/{username}")
    public String editPermissions(@PathVariable("username") String username, Model model){
        users user = usersService.findByUsername(username);
        List<roles> roles = rolesService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "users/edit_user";
    }

    @PostMapping("/user/update-permission")
    public String saveUserChanges(@RequestParam("id_user") int idUser, @RequestParam("role") roles role, RedirectAttributes attributes){

        // Updating the permission of the user
        users user = usersService.findByIdUsers(idUser);
        user.setRole(role);
        usersService.saveUser(user);
        attributes.addFlashAttribute("success_updated", "The "+role.getRole_name()+" role has been granted to the user "+user.getUsername());
        return "redirect:/all-users";
    }

    @PostMapping("/user/block-user")
    public String blockUser(@RequestParam("id_user") int idUser, RedirectAttributes attributes){
        users user = usersService.findByIdUsers(idUser);
        user.setEnabled(0);
        usersService.saveUser(user);
        attributes.addFlashAttribute("user_blocked", "User "+user.getUsername()+" has been disabled");
        return "redirect:/all-users";
    }

    @PostMapping("/user/allow-user")
    public String allowUser(@RequestParam("id_user") int idUser, RedirectAttributes attributes){
        users user = usersService.findByIdUsers(idUser);
        user.setEnabled(1);
        usersService.saveUser(user);
        attributes.addFlashAttribute("user_allowed", "User "+user.getUsername()+" has been enabled");
        return "redirect:/all-users";
    }

    //------------------------- DASHBOARDS
    @GetMapping("/dashboard/teacher")
    public String teacherdashboard(Model model, Authentication auth){
        users user = usersService.findByUsername(auth.getName());
        List<Object[]> pendingReviews = coursesService.getPendingReviewsForTeacher(user);
        List<pendingReviewDeliveries> listPending = new ArrayList<pendingReviewDeliveries>();

        for (Object[] object : pendingReviews) {
            pendingReviewDeliveries pending = new pendingReviewDeliveries((String) object[0], (String) object[1], (Long) object[2]);
            listPending.add(pending);
        }
        model.addAttribute("pendingReviews", listPending);

        return "users/teacher_dashboard";
    }

    @GetMapping("/dashboard/student")
    public String studentdashboard(Pageable pageable,Model model, Authentication auth){

        users user = usersService.findByUsername(auth.getName());
        Page<pendingActivitiesByStudent> pendingActivities = coursesService.getAllPendingActivities(user, pageable);
        model.addAttribute("activities", pendingActivities);

        return "users/student_dashboard";
    }
}
