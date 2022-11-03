package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

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
//        List<User> userList=userRepository.findAll();
//
//        return userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());
   return null;
    }

    @Override
    public UserDTO findByUserName(String username) {
//
//        User user=userRepository.findByUserName(username);
//
//        return userMapper.convertToDto(user);
        return null;
    }

    @Override
    public void save(UserDTO dto) {

        User user=userMapper.convertToEntity(dto);
        userRepository.save(user);

    }

    @Override
    public void deleteByUserName(String username) {

        userRepository.delete(userRepository.findByUserName(username));

    }
}
