package com.keldron.foxtrot;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    //creates views (temporary)
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/add-user").setViewName("add-user");
        registry.addViewController("/trainings-list").setViewName("trainings");
        registry.addViewController("/my-trainings").setViewName("my-trainings");
        registry.addViewController("/add-training").setViewName("add-training");
        registry.addViewController("/venues-list").setViewName("venues");
        registry.addViewController("/add-venue").setViewName("add-venue");
    }
    
}