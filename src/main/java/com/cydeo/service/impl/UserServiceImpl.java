package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

     private final UserRepository userRepository;
     private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> userList=userRepository.findAll(Sort.by("firstName"));

        return userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());

    }

    @Override
    public UserDTO findByUserName(String username) {

        User user=userRepository.findByUserName(username);

        return userMapper.convertToDto(user);

    }

    @Override
    public void save(UserDTO dto) {

        User user=userMapper.convertToEntity(dto);
        userRepository.save(user);

    }

    @Override
    public void deleteByUserName(String username) {

        userRepository.deleteByUserName(username);

    }
    @Override
    public UserDTO update(UserDTO user){ // this can be written as a void method
        // find current user
        User user1=userRepository.findByUserName(user.getUserName());
        // map update userDto to entity obj
        User convertedUser=userMapper.convertToEntity(user); // User has the id not UserDTO obj
        // set id to the convertedUser
        convertedUser.setId(user1.getId());
        // save the updated user in the db
        userRepository.save(convertedUser);
        return findByUserName(user.getUserName());
    }
}