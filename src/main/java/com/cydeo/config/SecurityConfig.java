package com.cydeo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) { // Spring security package interface...
        // UserDetails user1=new User();// this is the spring user not my user class
        List<UserDetails> userList = new ArrayList<>(); // manually we are creating the users
        userList.add(new User("mike", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")))); // import Spring security user not cydeo.user ...
        userList.add(new User("ozzy", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER")))); // import Spring security user not cydeo.user ...

        return new InMemoryUserDetailsManager(userList);// we are using this because we are not using the db yet
        // InMemoryUserDetailsManager is the impl of UserDetailsService // we are saving the users in the memory -- we hard coded the users here
    }
}
