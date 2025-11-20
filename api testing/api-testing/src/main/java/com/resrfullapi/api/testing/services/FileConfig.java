package com.resrfullapi.api.testing.services;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileConfig implements WebMvcConfigurer {

    String IMAGE_FOLDER_PATH = "file_upload/images/";
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file_upload/**")
                .addResourceLocations("file:"+IMAGE_FOLDER_PATH);
    }
}
