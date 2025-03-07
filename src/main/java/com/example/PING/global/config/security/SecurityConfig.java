package com.example.PING.global.config.security;

import com.example.PING.global.security.filter.JwtAuthenticationFilter;
import com.example.PING.global.security.handler.EmailPasswordSuccessHandler;
import com.example.PING.global.security.provider.EmailPasswordAuthenticationProvider;
import com.example.PING.global.security.provider.JwtProvider;
import com.example.PING.global.security.utils.JwtUtil;
import com.example.PING.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig { //Todo 채현이가 탐구해야 하는 클래스

    private final UserService userService;
    private final EmailPasswordSuccessHandler emailPasswordSuccessHandler;
    private final JwtUtil jwtUtil;

    // Todo allowUrls 수정해야 함. 현재 끼니 기준으로 되어 있음.
    private String[] allowUrls = {"/", "/favicon.ico",
            "/api/v1/auth/oauth/**", "/swagger-ui/**", "/v3/**"};

    @Value("${cors-allowed-origins}")
    private List<String> corsAllowedOrigins;

    // 1. Password encoder
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. AuthenticationProvider
    @Bean
    public EmailPasswordAuthenticationProvider emailPasswordAuthenticationProvider() {
        return new EmailPasswordAuthenticationProvider(userService);
    }

    @Bean
    public JwtProvider jwtTokenProvider() {
        return new JwtProvider(jwtUtil, userService);
    }

    // 3. AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .authenticationProvider(emailPasswordAuthenticationProvider())
                .authenticationProvider(jwtTokenProvider());
        authenticationManagerBuilder.parentAuthenticationManager(null);
        return authenticationManagerBuilder.build();
    }

    // 4. CORS Configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(corsAllowedOrigins);
        configuration.addAllowedMethod("*");
        configuration.setAllowedHeaders(List.of("*")); // 허용할 헤더
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 적용
        return source;
    }

    // 5. Web Security Customizer
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().requestMatchers(allowUrls);
    }

    // 6. SecurityFilterChain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(customizer -> customizer.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(allowUrls).permitAll()
                        .anyRequest().authenticated());

        http
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint((request, response, authException) ->
                                response.setStatus(HttpStatus.UNAUTHORIZED.value())));

        http.authenticationManager(authenticationManager(http));

        http
//                .addFilterAt(emailPasswordAuthenticationFilter(authenticationManager(http)), UsernamePasswordAuthenticationFilter.class)
                // Todo 이거 나중에 일반 로그인 용도로 사용할 코드입니다. 지우지 말아주세요!
                .addFilterBefore(jwtAuthenticationFilter(authenticationManager(http)), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Todo 7. EmailPasswordAuthenticationFilter 이거 나중에 일반로그인 용도로 사용할 것임. 3월 5일 기준 아직 사용 안 하는 파일입니다.
    // Todo 그래도 지우지 말고 남겨주세요~!!
//    @Bean
//    public EmailPasswordAuthenticationFilter emailPasswordAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
//        EmailPasswordAuthenticationFilter filter = new EmailPasswordAuthenticationFilter(authenticationManager);
//        filter.setFilterProcessesUrl("/api/v1/auth/admin/login");
//        filter.setAuthenticationSuccessHandler(emailPasswordSuccessHandler);
//        filter.afterPropertiesSet();
//        return filter;
//    }

    // 8. JwtAuthenticationFilter
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(authenticationManager);
        filter.afterPropertiesSet();
        return filter;
    }
}
