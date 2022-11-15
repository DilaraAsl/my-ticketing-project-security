package com.cydeo.service.impl;

import com.cydeo.entity.User;
import com.cydeo.entity.common.UserPrincipal;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // User from the entity class- we find the user obj
        User user=userRepository.findByUserNameAndIsDeleted(username,false);
        // authenticate the user
        if(user==null){
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user); // we convert the user entity to spring user obj threw UserPrincipal class
    }
}
