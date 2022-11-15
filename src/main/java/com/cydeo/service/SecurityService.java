package com.cydeo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityService extends UserDetailsService { // UserDetailService interface has loadByUserName method
    // we will override that method in the implementation
}
