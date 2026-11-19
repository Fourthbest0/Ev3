package com.idgs17.rate.config;

import com.idgs17.rate.security.GatewayJwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegistrationConfig {
    @Bean
    FilterRegistrationBean<GatewayJwtFilter> disableContainerRegistration(GatewayJwtFilter filter) {
        FilterRegistrationBean<GatewayJwtFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }
}
