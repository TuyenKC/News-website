package com.newsproject.service;


import com.newsproject.controller.dto.LogInDto;
import com.newsproject.controller.dto.SignUpDto;
import com.newsproject.controller.dto.UsersDto;
import com.newsproject.repository.entity.Users;
import org.springframework.http.ResponseEntity;

public interface UsersService {
    public Users findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Users saveOrUpdate(Users user);
    public ResponseEntity<?> addUser(UsersDto usersCRUDDto);
    public ResponseEntity<?> getUsersById(String id);
    public ResponseEntity<?> getAllUsers();
    public ResponseEntity<?> deleteUsersById(String id);
    public ResponseEntity<?> updateUser(String id, UsersDto usersCRUDDto);

    public ResponseEntity<?> signUpUser(SignUpDto signUpDto);
    public ResponseEntity<?> logInUser(LogInDto loginDto);

}
