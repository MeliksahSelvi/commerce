package com.commerce.user.service.common.security.config;

import com.commerce.user.service.common.security.filter.CustomAuthenticationFilter;
import com.commerce.user.service.common.security.handler.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static List<String> openApiEndpoints = List.of("/api/v1/auth/login", "/api/v1/auth/register","/api/v1/roles", "/swagger-ui/**",
            "/swagger-ui.html", "/v3/api-docs/**");

    private final CustomAuthenticationFilter authenticationFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint entryPoint;

    public SecurityConfig(CustomAccessDeniedHandler accessDeniedHandler, AuthenticationEntryPoint entryPoint,
                          CustomAuthenticationFilter authenticationFilter) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.entryPoint = entryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).cors(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(request -> request.requestMatchers(openApiEndpoints.toArray(new String[0])).permitAll());
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(entryPoint).accessDeniedHandler(accessDeniedHandler));
        http.addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.
                        addMapping("/**").
                        allowedOrigins("http://localhost", "http://localhost:4200").
                        allowedMethods("POST", "GET", "OPTIONS", "HEAD", "DELETE", "PUT", "PATCH").
                        allowCredentials(true);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
