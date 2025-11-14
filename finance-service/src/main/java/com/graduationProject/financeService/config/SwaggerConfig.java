package com.graduationProject.financeService.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Finance Service API")
                .version("1.0")
                .description("Finance Service API for ERP System - Invoice, Budget, Expense, and Payroll Management"));
    }
}

