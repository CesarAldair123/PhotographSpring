package com.example.Photograph.service;

import com.example.Photograph.dto.UserDto;
import com.example.Photograph.mapper.UserMapper;
import com.example.Photograph.model.User;
import com.example.Photograph.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    @Transactional
    public List<UserDto> findUsers(@Nullable String username){
        List<User> users;
        if(username == null)
            users = userRepository.findAll();
        else
            users = userRepository.findAllByUsername(username);
        return users.stream()
                .map(userMapper::userToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto findUserById(int id){
        User user =  userRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return userMapper.userToDto(user);
    }

}
