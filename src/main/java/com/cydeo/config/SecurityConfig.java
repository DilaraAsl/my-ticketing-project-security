package com.cydeo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {
// we are creating of beans from the springframework security package for authentication and authorization
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) { // Spring security package interface...
//        // UserDetails user1=new User();// this is the spring user not my user class
//        List<UserDetails> userList = new ArrayList<>(); // manually we are creating the users
//        userList.add(new User("mike", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")))); // import Spring security user not cydeo.user ...
//        userList.add(new User("ozzy", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER")))); // import Spring security user not cydeo.user ...
//
//        return new InMemoryUserDetailsManager(userList);// we are using this because we are not using the db yet
//        // InMemoryUserDetailsManager is the impl of UserDetailsService // we are saving the users in the memory -- we hard coded the users here
//    }





    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeRequests()// when we run security we need to authorize the pages - not every user can access every page or every file
//                .antMatchers("/user/**").hasRole("ADMIN") // anything under the usercontroller- all pages will be accessible by admin.. // we don't need to place ROLE_ in the implementation it is auto added
//                .antMatchers("/project/**").hasRole("MANAGER") // anything under the ProjectController- all pages will be accessible by manager..
//                .antMatchers("/task/**").hasRole("MANAGER") // anything under the TaskController- all pages will be accessible by the manager..
//                .antMatchers("/task/employee/**").hasRole("EMPLOYEE")
        // .antMatchers("/task/**").hasAnyRole("EMLOYEE","MANAGER") // String ... employee list
        // we are not using role because in the sql we entered
                .antMatchers("/task/**").hasAuthority("Admin") // ROLE_  is defined in this format when we use has authority role definition should match the user entity defined roles
                .antMatchers("/task/**").hasAuthority("Manager")
                .antMatchers("/task/employee/**").hasAuthority("Employee")


                .antMatchers( // list the pages that is accessible to all users
                        "/",
                        "/login",
                        "/fragments/**",
                        "/assets/**",
                        "/images/**"
                ).permitAll() // permit access to all -- the pages defined above can be reachable by all users
                .anyRequest().authenticated()
                .and()
                //.httpBasic() // any other page that is not in the list above should be authenticated... pop up box appears requires authentication to access the welcome page for example...
        // we are authenticating here through httpBasic--- whenever we open the app spring security is taking over httpBasic is a pop up..
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/welcome")
                .failureUrl("/login?error=true")
                .permitAll()// login and welcome pages should be accessible to all users
                .and().build();
    }
}
