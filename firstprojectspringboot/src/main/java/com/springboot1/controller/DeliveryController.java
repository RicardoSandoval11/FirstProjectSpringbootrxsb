package com.springboot1.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springboot1.model.activity;
import com.springboot1.model.delivery;
import com.springboot1.model.users;
import com.springboot1.service.IActivityService;
import com.springboot1.service.IDeliveryService;
import com.springboot1.service.IUsersService;
import com.springboot1.util.Util;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class DeliveryController {

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IDeliveryService deliveryService;

    @Autowired
    private IUsersService usersService;

    // Path to save the uploaded files
    @Value("${coursesapp.path.files}")
    private String filesPath;
    
    @GetMapping("/delivery/{id}")
    public String addDelivery(@PathVariable("id") int idActivity, Authentication auth,Model model){
        String username = auth.getName();
        users user = usersService.findByUsername(username);
        activity activity = activityService.findActivityById(idActivity);
        model.addAttribute("activity", activity);
        model.addAttribute("userId", user.getId_user());
        return "delivery/make_delivery";
    }

    @PostMapping("/save-delivery")
    public String saveDelivery(
                    @RequestParam("work") MultipartFile workFile, 
                    @RequestParam("id_activity") int idActivity, 
                    @RequestParam("id_user") int idUser,
                    @RequestParam("comments") String comments
                    ){

        users user = usersService.findByIdUsers(idUser);
        activity activity = activityService.findActivityById(idActivity);
        delivery delivery = deliveryService.getDeliveriesByUserAndActivity(user, activity);

        // Save work file
        if(!workFile.isEmpty()){
            String fileName = Util.saveFile(workFile, filesPath);
            if (fileName != null) {
                delivery.setWork(fileName);
            }
        }

        // Save comments of the student
        if (comments != null) {
            delivery.setComments(comments);
        }

        // Set current date as delivery date
        Calendar cal = Calendar.getInstance();
        Date currentDate=cal.getTime();
        delivery.setDate(currentDate);

        // Change statusof delivery
        delivery.setPending(0);

        // Saving updated delivery
        deliveryService.saveDelivery(delivery);

        return "redirect:/view-activity/"+idActivity;
    }

    @GetMapping("/view-deliveries/{id}")
    public String viewDeliveries(@PathVariable("id") int id_activity, Model model){

        // Getting the activity
        activity activity = activityService.findActivityById(id_activity);

        // getting the deliveries
        List<delivery> deliveries = deliveryService.getDeliveriesByActivity(activity);

        // Adding to the template
        model.addAttribute("deliveries", deliveries);
        
        return "delivery/activity_deliveries";
    }

    @GetMapping("/qualify/{id}")
    public String viewDelivery(@PathVariable("id") int idDelivery, Model model){
        delivery delivery = deliveryService.getDeliveryById(idDelivery);
        model.addAttribute("delivery", delivery);

        // verifying if activity has not closed yet:
        Date end_date_activity = delivery.getActivity().getEnd_date();
        Calendar cal = Calendar.getInstance();
        Date currentDate=cal.getTime();
        if (end_date_activity.compareTo(currentDate) < 0) {
            model.addAttribute("hasFinished", "yes");
        }else{
            model.addAttribute("hasFinished", "no");
        }
        return "delivery/qualify_delivery";
    }

    @PostMapping("/save-grade")
    public String saveGrade(
            @RequestParam("grade") int grade, 
            @RequestParam("id_delivery") int idDelivery, 
            RedirectAttributes attributes
    ){
        // Getting the delivery
        delivery delivery = deliveryService.getDeliveryById(idDelivery);

        // Set the grade
        delivery.setGrade(grade);

        // Saving the grade
        deliveryService.saveDelivery(delivery);

        return "redirect:/qualify/"+delivery.getId_delivery();
    }
}
