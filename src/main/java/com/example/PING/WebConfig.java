package com.example.PING;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    private final String ipAddress = "localhost"; // 로컬 테스트용 IP
    private final String ipAddress = "43.203.51.237"; // CI/CD 배포를 위한 IP 주소
    private final String frontEndPort = "5173"; // React 앱이 실행되는 포트


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedOrigins("http://" + this.ipAddress + ":" + this.frontEndPort);
    }
}
