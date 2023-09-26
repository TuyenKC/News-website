package com.newsproject.service;


import com.newsproject.controller.dto.JwtResponse;
import com.newsproject.controller.dto.LogInDto;
import com.newsproject.controller.dto.SignUpDto;
import com.newsproject.controller.dto.UsersDto;
import com.newsproject.exception.UsernameValidateException;
import com.newsproject.exception.UsersNotExistedException;
import com.newsproject.repository.entity.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsersService {
    public Users findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Users saveOrUpdate(Users user);
    public UsersDto addUser(UsersDto usersCRUDDto) throws UsernameValidateException;
    public UsersDto getUsersById(String id) throws UsersNotExistedException;
    public List<UsersDto> getAllUsers();
    public void deleteUsersById(String id) throws UsersNotExistedException;
    public UsersDto updateUser(String id, UsersDto usersCRUDDto) throws UsersNotExistedException;

    public void signUpUser(SignUpDto signUpDto) throws UsernameValidateException;
    public JwtResponse logInUser(LogInDto loginDto) ;

}
