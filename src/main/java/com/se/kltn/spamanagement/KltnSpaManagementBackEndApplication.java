package com.se.kltn.spamanagement;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KltnSpaManagementBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(KltnSpaManagementBackEndApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapperConfig() {
        return new ModelMapper();
    }

}
