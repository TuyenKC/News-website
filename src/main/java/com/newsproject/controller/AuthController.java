package com.newsproject.controller;

import com.newsproject.controller.dto.JwtResponse;
import com.newsproject.controller.dto.LogInDto;
import com.newsproject.controller.dto.ResponseObject;
import com.newsproject.controller.dto.SignUpDto;
import com.newsproject.repository.entity.ERole;
import com.newsproject.repository.entity.Roles;
import com.newsproject.repository.entity.Users;
import com.newsproject.security.CustomUserDetails;
import com.newsproject.service.RolesService;
import com.newsproject.service.UsersService;
import com.newsproject.utils.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        try{
            usersService.signUpUser(signUpDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Register successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LogInDto loginDto) {
        try{
            usersService.logInUser(loginDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Login successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }

}
