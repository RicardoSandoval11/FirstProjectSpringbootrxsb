package com.springboot1.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot1.model.activity;
import com.springboot1.model.courses;
import com.springboot1.model.delivery;
import com.springboot1.model.users;
import com.springboot1.service.IActivityService;
import com.springboot1.service.ICoursesService;
import com.springboot1.service.IDeliveryService;
import com.springboot1.service.IUsersService;

@Controller
public class ActivitiesController {

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IDeliveryService deliveryService;

    @Autowired
    private ICoursesService coursesService;

    @Autowired
    private IUsersService usersService;
    
    @GetMapping("/my-activities")
    public String showActivities(Model model) {

        return "activities/my_activities.html";
    }

    @GetMapping("/view-activity/{id}")
    public String viewActivity(@PathVariable("id") int idActivity, Model model, Authentication auth){
        String username = auth.getName();
        users user = usersService.findByUsername(username);
        activity activity = activityService.findActivityById(idActivity);
        delivery delivery = deliveryService.getDeliveriesByUserAndActivity(user, activity);
        Calendar cal = Calendar.getInstance();
        Date currentDate=cal.getTime();
        if (currentDate.compareTo(activity.getEnd_date()) > 0) {
            model.addAttribute("hasFinished", "yes");
        }else{
            model.addAttribute("hasFinished", "no");
        }
        model.addAttribute("activity", activity);
        model.addAttribute("delivery", delivery);
        return "activities/activity";
    }

    @GetMapping("/create-activity/{id}")
    public String createActivity(@ModelAttribute activity activity,Model model, @PathVariable("id") int idCourse){
        courses course = coursesService.getCourseById(idCourse);
        model.addAttribute("course", course);
        return "activities/create_activity";
    }

    @PostMapping("/save-activity")
    public String saveActivity(@ModelAttribute activity activity, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()) {
            return "activities/create_activity";
        }
        // Saving the activity
        activityService.saveActivity(activity);

        attributes.addFlashAttribute("activity", activity.getName());
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String successMessage(){
        return "activities/successfully_created";
    }

    @GetMapping("/assign/{id}")
    public String assignActivityForm(@PathVariable("id") int idActivity, Model model){
        activity activity = activityService.findActivityById(idActivity);
        courses course = activity.getCourse();
        Integer courseId = course.getId_course();
        // Add objects to the template
        model.addAttribute("course_id", courseId);
        model.addAttribute("activity_id", idActivity);
        return "activities/assign";
    }

    @PostMapping("/assigned")
    public String completeAssignProcess(@RequestParam("course_id") Integer CourseId,@RequestParam("activity_id") Integer ActivityId){

        // getting the activity
        activity activity = activityService.findActivityById(ActivityId);

        // Getting the users of the course
        courses course = coursesService.getCourseById(CourseId);
        List<users> users = course.getUsers();
         
        // Creating an empty deliveries list
        List<delivery> deliveries = new ArrayList<>();
         
        for (users user : users) {
            delivery delivery = new delivery();
             if (deliveryService.getDeliveriesByUserAndActivity(user, activity) == null) {
                 delivery.setActivity(activity);
                 delivery.setPending(1);
                 delivery.setUser(user);
                 deliveries.add(delivery);
             }
        }
        
        // Saving deliveries
        deliveryService.createDeliveries(deliveries);

        // Change activity as assigned
        activity.setAssigned(1);
        activityService.updateActivity(activity);

        return "redirect:course/"+CourseId;
        //return "redirect:/";
    }

    @GetMapping("/update-activity/{id}")
    public String updateActivity(@PathVariable("id") int idActivity, Model model){
        activity activity = activityService.findActivityById(idActivity);
        model.addAttribute("activity", activity);
        return "activities/update_form";
    }
    @PostMapping("/activity/save-changes")
    public String updateActivity(@ModelAttribute activity activity, BindingResult result, RedirectAttributes attributes,Model model){

        if (result.hasErrors()) {
            return "activities/update_form";
        }

        // Update record
        activityService.updateActivity(activity);

        // Redirect user to view activity page
        attributes.addFlashAttribute("msg", activity.getName()+" has been updated successfully!");
        return "redirect:/view-activity/"+activity.getId_activity();

    }

    @GetMapping("/delete-activity/{id}")
    public String deleteActivity(@PathVariable("id") int idActivity, Model model){

        // Getting the activity
        activity activity = activityService.findActivityById(idActivity);

        model.addAttribute("activity_name", activity.getName());
        model.addAttribute("activity_id", activity.getId_activity());

        return "activities/confirmation";

    }

    @PostMapping("/activity/delete-confirmation")
    public String deleteConfirmed(@RequestParam("id_activity") int idActivity, RedirectAttributes attributes){

        // Find the activity
        activity activity = activityService.findActivityById(idActivity);

        // Saving the id of the course
        Integer id_course = activity.getCourse().getId_course();

        String deletedActivity = activity.getName();

        // Delete deliveries
        List<delivery> courseDeliveries = deliveryService.getDeliveriesByActivity(activity);
        deliveryService.deleteDeliveriesOfActivity(courseDeliveries);

        // Delete activity
        activityService.deleteActivity(idActivity);

        attributes.addFlashAttribute("success_deleted_activity", "The activity "+deletedActivity+" has been deleted successfully");

        return "redirect:/course/"+id_course;

    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}

