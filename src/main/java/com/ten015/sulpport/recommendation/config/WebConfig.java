package com.ten015.sulpport.recommendation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings (CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에
                .allowedOrigins("http://110.165.18.226") // ip 허용한다
                .allowedMethods("GET", "POST","DELETE", "PUT") // 허용 메서드
                .allowedHeaders("*") // 허용 헤더
                .allowCredentials(true); //쿠키 포함 요청 허용
    }

}
