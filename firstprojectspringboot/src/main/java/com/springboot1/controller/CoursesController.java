package com.springboot1.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot1.customObjects.customCourseDTO;
import com.springboot1.model.activity;
import com.springboot1.model.category;
import com.springboot1.model.courses;
import com.springboot1.model.roles;
import com.springboot1.model.users;
import com.springboot1.service.IActivityService;
import com.springboot1.service.ICategoryService;
import com.springboot1.service.ICoursesService;
import com.springboot1.service.IRolesService;
import com.springboot1.service.IUsersService;
import com.springboot1.util.Util;;

@Controller
public class CoursesController {

    @Autowired
    private ICoursesService CoursesService;

    @Autowired
    private IUsersService usersService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IRolesService rolesService;

    @Value("${coursesapp.path.images}")
    private String coverImagesPath;

    
    @GetMapping("/my-courses")
    public String myCourses(Model model, Authentication auth){

        
        String username= auth.getName();
        users user = usersService.findByUsername(username);

        // Creating courses list
        List<courses> courses = new ArrayList<courses>();
        
        //Get roles
        roles role = user.getRole();

        if (role.getRole_name().equals("teacher")) {
            courses = CoursesService.getCoursesByTeacher(user);

        }else{
            courses = CoursesService.courseByUser(user);
        }
        model.addAttribute("courses", courses);
        return "courses/my_courses";
    }

    @GetMapping("/course/view/enroll/{id}")
    public String showCourseDetails(@PathVariable("id") int id_course, Model model){
        courses course = CoursesService.getCourseById(id_course);
        
        model.addAttribute("course", course);
        model.addAttribute("enrolled_students", course.getUsers().size());

        return "courses/course_details";
    }

    @PostMapping("/save-course/{id}")
    public String enrollCourse(@PathVariable("id") int id_course, Authentication auth, RedirectAttributes attributes){
        // Getting the user
        users user = usersService.findByUsername(auth.getName());

        // Getting the course
        courses course = CoursesService.getCourseById(id_course);

        List<courses> user_courses = user.getCourses();
        if (!user_courses.contains(course) && !user.getRole().getRole_name().equals("teacher")) {

            if (user.getRole().getRole_name().equals("administrator")) {
                return "redirect:/";
            }

            // Enrolling new user
            user_courses.add(course);
            user.setCourses(user_courses);
            usersService.UpdateCourses(user);
        }else{
            attributes.addFlashAttribute("failed_enrolling_msg", "You need to register as a student, in order to enroll in a course");
            return "redirect:/my-courses";
        }

        return "redirect:/my-courses";
    }

    @GetMapping("/course/{id}")
    public String viewCourse(@PathVariable("id") int id_course, Model model, Authentication auth){
        courses course = CoursesService.getCourseById(id_course);
        users user = usersService.findByUsername(auth.getName());
        List<courses> userCourses = user.getCourses();


        if (!userCourses.contains(course) && !course.getTeacher().getEmail().equals(user.getEmail())) {
            return "redirect:/";
        }
        List<activity> activities = activityService.getAllActivitiesByCourse(course);
        model.addAttribute("activities", activities);
        model.addAttribute("course", course);
        //model.addAttribute("pending", pendingReviews);
        return "courses/course";
    }

    @GetMapping("/create-course-dashboard")
    public String coursesDashboard(Model model){
        return "courses/dashboard";
    }

    @GetMapping("/create-new-course")
    public String createNewCourse(courses course,Model model){
        model.addAttribute("course", course);
        return "courses/create_course";
    }

    @PostMapping("/save-new-course")
    public String saveNewCourse(@ModelAttribute("course") courses course, BindingResult result, RedirectAttributes attributes, @RequestParam("coverImage") MultipartFile multipartFile){
        if (result.hasErrors()) {
            System.out.println("Error: "+result);
            return "courses/create_course";
        }
        
        //Save course
        if (!multipartFile.isEmpty()) {
            String fileName = Util.saveFile(multipartFile, coverImagesPath);
            if (fileName != null) {
                course.setCover_image(fileName);
            }
        }
        CoursesService.saveCourse(course);
        attributes.addFlashAttribute("course_saved_msg", "The course "+course.getName()+" has been successfully saved!");

        return "redirect:/courses/all";
    }

    @GetMapping("/courses/all")
    public String showAllCourses(Pageable page, Model model){
        Page<customCourseDTO> courses = CoursesService.getAllCourses(page);
        model.addAttribute("courses", courses);
        for (Object course : courses) {
            System.out.println(course);
        }
        return "courses/all";
    }

    @GetMapping("/course/edit/{id}")
    public String editCourse(@PathVariable("id") int idCourse, Model model){
        courses course = CoursesService.getCourseById(idCourse);
        model.addAttribute("course", course);
        return "courses/create_course";
    }

    @GetMapping("/delete-course/{id}")
    public String deleteCourse(@PathVariable("id") int idCourse, RedirectAttributes attributes){
        courses course = CoursesService.getCourseById(idCourse);
        try {
            CoursesService.deleteCourse(course);
            attributes.addFlashAttribute("delete_msg", "The "+course.getName()+" has been removed successfully");
            return "redirect:/courses/all";
            
        } catch (Exception e) {
            attributes.addFlashAttribute("delete_msg", "The "+course.getName()+" cannot be removed beacuse it has users");
            return "redirect:/courses/all";
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @ModelAttribute
    public void seGenericAttributes(Model model){
        List<category> categories = categoryService.getAllCategories();
        // getting teachers
        roles role = rolesService.getRoleById(2);
        List<users> teachers = usersService.getUsersByRole(role);
        model.addAttribute("teachers", teachers);
        model.addAttribute("categories", categories);
    }

}
