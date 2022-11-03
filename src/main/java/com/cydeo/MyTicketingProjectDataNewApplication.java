package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyTicketingProjectDataNewApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyTicketingProjectDataNewApplication.class, args);
    }
    @Bean
    public ModelMapper mapper() { // return type should be the class type
        return new ModelMapper();
    }
}
