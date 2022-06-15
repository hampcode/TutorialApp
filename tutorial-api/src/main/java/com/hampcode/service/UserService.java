package com.hampcode.service;

import com.hampcode.converter.UserConverter;
import com.hampcode.dto.LoginRequestDto;
import com.hampcode.dto.LoginResponseDto;
import com.hampcode.exception.GeneralServiceException;
import com.hampcode.exception.IncorrectRequestException;
import com.hampcode.exception.NotFoundException;
import com.hampcode.model.User;
import com.hampcode.repository.UserRepository;
import com.hampcode.validators.UserValidator;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Value("${jwt.password}")
    private String jwtSecret;

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserConverter userConverter, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(User user) {
        try {
            UserValidator.validate(user);
            User existUser=userRepository.findByUserName(user.getUserName())
                    .orElse(null);
            if(existUser!=null)
                throw new IncorrectRequestException("El nombre usuario ya existe");

            String encoder=passwordEncoder.encode(user.getPassword());
            user.setPassword(encoder);

            return userRepository.save(user);
        } catch (IncorrectRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public List<User> findAll(){
        try {
            return userRepository.findAll();
        } catch (IncorrectRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public LoginResponseDto login(LoginRequestDto request){
        try {
            User user=userRepository.findByUserName(request.getUserName())
                    .orElseThrow(()->new IncorrectRequestException("Usuario o password incorrecto"));

            if(!passwordEncoder.matches(request.getPassword(),user.getPassword()))
                throw new IncorrectRequestException("Usuario o password incorrectos");

            String token =createToken(user);

            return new LoginResponseDto(userConverter.convertEntityToDto(user),token);

        } catch (IncorrectRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public String createToken(User user){
        Date now =new Date();
        Date expiryDate=new Date(now.getTime()+ (1000*60*60*24));

        return Jwts.builder()
                .setSubject(user.getUserName())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512,jwtSecret).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (UnsupportedJwtException e) {
            //log.error("JWT in a particular format/configuration that does not match the format expected");
        }catch (MalformedJwtException e) {
            //log.error(" JWT was not correctly constructed and should be rejected");
        }catch (SignatureException e) {
            //log.error("Signature or verifying an existing signature of a JWT failed");
        }catch (ExpiredJwtException e) {
            //log.error("JWT was accepted after it expired and must be rejected");
        }
        return false;
    }

    public String getUsernameFromToken(String jwt) {
        try {
            return Jwts.parser().setSigningKey(jwtSecret)
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new IncorrectRequestException("Invalid Token");
        }
    }



}
