package com.springboot1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Value("${coursesapp.path.images}")
    private String imgsPath;

    @Value("${coursesapp.path.files}")
    private String filesPath;

    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/cover-imgs/**").addResourceLocations("file:"+imgsPath);
        registry.addResourceHandler("/files/**").addResourceLocations("file:"+filesPath);
    }
    
}
