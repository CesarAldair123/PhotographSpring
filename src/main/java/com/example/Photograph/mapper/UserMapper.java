package com.example.Photograph.mapper;

import com.example.Photograph.dto.UserDto;
import com.example.Photograph.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToDto(User user);

}
