package com.example.PING;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    private final String ipAddress = "43.203.51.237"; // 배포용
    private final String ipAddress = "localhost";
    private final String frontEndPort = "5173"; // React 앱이 실행되는 포트


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://" + this.ipAddress + ":" + this.frontEndPort)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }
}
