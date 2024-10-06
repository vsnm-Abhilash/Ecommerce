package com.abhilash.ecommerce.order_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig {

    @Bean
    public ModelMapper getMapper(){
        return new ModelMapper();
    }
}
