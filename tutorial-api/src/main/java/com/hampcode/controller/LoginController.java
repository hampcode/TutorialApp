package com.hampcode.controller;

import com.hampcode.converter.UserConverter;
import com.hampcode.dto.*;
import com.hampcode.model.User;
import com.hampcode.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final UserService userService;

    private final UserConverter userConverter;

    public LoginController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        User user = userService.createUser(userConverter.signup(signupRequestDto));
        return new ResponseEntity<>(userConverter.convertEntityToDto(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request){
        LoginResponseDto loginResponseDto=userService.login(request);
        return new ResponseEntity<LoginResponseDto>(loginResponseDto, HttpStatus.OK);
    }

}
