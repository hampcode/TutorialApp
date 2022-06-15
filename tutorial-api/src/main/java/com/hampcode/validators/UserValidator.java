package com.hampcode.validators;


import com.hampcode.exception.IncorrectRequestException;
import com.hampcode.model.User;

public class UserValidator {
    public static void validate(User user){
        if(user.getUserName()==null || user.getUserName().trim().isEmpty()){
            throw new IncorrectRequestException("El nombre de usuario es requerido");
        }

        if(user.getUserName().length()>30){
            throw new IncorrectRequestException("El nombre de usuario es muy largo (max 30)");
        }

        if(user.getPassword()==null || user.getPassword().isEmpty()){
            throw new IncorrectRequestException("El password es requerido");
        }
        if(user.getPassword().length()>30){
            throw new IncorrectRequestException("El password de usuario es muy largo (max 30)");
        }
    }
}