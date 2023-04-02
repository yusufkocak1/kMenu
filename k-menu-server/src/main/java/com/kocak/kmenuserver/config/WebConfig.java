package com.kocak.kmenuserver.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final long MAX_SIZE = 52428800; // 50MB

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofBytes(MAX_SIZE));
        factory.setMaxRequestSize(DataSize.ofBytes(MAX_SIZE));
        return factory.createMultipartConfig();
    }
}
