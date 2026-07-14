package com.example.lockstock.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer { //resource에 대해서 커스텀할 수 있게 해주는 인터페이스
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // file:///home/ubuntu/images/ 경로를 /images/** URL로 접근 가능하게 매핑
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///D:/test/"); //file:///home/ubuntu/images/
        //백엔드 내에서의 실제경로를 웹에서 별명처럼 쓸 수 있게 해주는 것
    }
}