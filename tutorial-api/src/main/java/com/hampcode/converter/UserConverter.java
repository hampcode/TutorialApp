package com.hampcode.converter;

import com.hampcode.dto.SignupRequestDto;
import com.hampcode.dto.UserDto;
import com.hampcode.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private final ModelMapper modelMapper;

    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto convertEntityToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User convertDtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public UserDto signup(SignupRequestDto signupRequestDto){
        return modelMapper.map(signupRequestDto, UserDto.class);
    }
}
