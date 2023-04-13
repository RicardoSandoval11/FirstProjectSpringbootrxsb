package com.springboot1.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DatabaseWebSecurity {
    
    @Bean
    public UserDetailsManager users(DataSource dataSource){
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery("select username, password, enabled from users where username=?");
		users.setAuthoritiesByUsernameQuery("select users.username, roles.role_name from users inner join roles on roles.id_role=users.role where users.username = ?");
		return users;
    }
    
    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
		// Static resources
		.requestMatchers("/css/**", "/js/**", "/tinymce/**","/cover-imgs/**").permitAll()

		// Public views
		.requestMatchers("/","/register","/save-user","/courses","/course/view/enroll/**","/courses?**","/filter/**").permitAll()
    
		// Permissions by role
		.requestMatchers("/admin-panel/**",
									"/all-users",
									"/edit-permission/**",
									"/user/block-user",
									"/user/allow-user",
									"/create-course-dashboard",
									"/categories/list-all/**",
									"/categories/**",
									"/courses/all/**",
									"/course/edit/**",
									"/delete-course/**").hasAnyAuthority("administrator")
		.requestMatchers("/my-courses","/course/**").hasAnyAuthority("teacher","student")
		.requestMatchers("/edit-activity/**","/delete-activity/**","/dashboard/teacher/**").hasAnyAuthority("teacher")
		.requestMatchers("/dashboard/student/**").hasAnyAuthority("student")
		//All views neeed authentication
        .anyRequest().authenticated()
        // Authentication views are public 
		.and().formLogin().loginPage("/login").defaultSuccessUrl("/",true).permitAll()
        .and().logout().permitAll();

		return http.build();
	}

	// used to encode passwords
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
    
}
