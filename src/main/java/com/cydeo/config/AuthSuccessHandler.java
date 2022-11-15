package com.cydeo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    // we want each role to see a different page when the login is successful - we want to customize
    // we need to provide authentication success handler to security config method
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles= AuthorityUtils.authorityListToSet(authentication.getAuthorities());//whenever authentication is done capture the user role
        // user can have more than one role - we are using set

        if(roles.contains("Admin")){
            response.sendRedirect("/user/create");
        }
        if(roles.contains("Manager")){
            response.sendRedirect("/task/create");
        }
        if(roles.contains("Employee")){
            response.sendRedirect("/task/employee/pending-task");
        }
    }
}
