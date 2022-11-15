package com.cydeo.entity.common;

import com.cydeo.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails { // UserDetails is an interface which User implements
    // we are overriding the methods to convert the entity which comes from the db to spring user obj in this class
    // by overriding the methods

    private User user;

    public UserPrincipal(User user) { // accepts user object to convert it to spring user object type
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // authorization process, get the roles so that each role can access a certain page..
        List<GrantedAuthority> authorityList=new ArrayList<>();

        GrantedAuthority authority=new SimpleGrantedAuthority(this.user.getRole().getDescription()); // we are getting the role description from the db
        authorityList.add(authority);
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.user.getPassWord();
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //if it is false spring security will not authenticate the app
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //if it is false spring security will not authenticate the app
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //if it is false spring security will not authenticate the app
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }
}
