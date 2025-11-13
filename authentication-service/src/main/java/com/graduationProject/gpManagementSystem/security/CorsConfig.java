package com.graduationProject.gpManagementSystem.security;

    
// import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer getCorsConfiguration(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Allow frontend origin

                        // .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*") // Enable CORS for the whole application.
                        .allowCredentials(true)// ✅ Required for WebSockets


                        .exposedHeaders("Authorization") // Expose required headers
                        .allowedOriginPatterns("*");  // Try this if allowedOrigins isn't enough
            }
        };
    }
}

