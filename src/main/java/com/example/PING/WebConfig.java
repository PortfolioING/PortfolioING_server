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
                .allowedOrigins("http://" + this.ipAddress + ":" + this.frontEndPort)
                .allowCredentials(true); // 브라우저에서 frontend js 코드가 응답 자체에 접근할 수 있게 허용함
    }
}
