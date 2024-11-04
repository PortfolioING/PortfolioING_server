package com.example.PING;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String ipAddress = "localhost"; // 또는 실제 IP 주소
    private final String frontEndPort = "3000"; // React 앱이 실행되는 포트

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("http://" + this.ipAddress + ":" + this.frontEndPort);
    }
}
