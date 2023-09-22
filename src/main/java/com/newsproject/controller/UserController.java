package com.newsproject.controller;

import com.newsproject.controller.dto.UsersDto;
import com.newsproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UsersService usersService;
    @PostMapping("/admin/users")
    public ResponseEntity<?> addUser(@RequestBody UsersDto usersDto){
        return usersService.addUser(usersDto);
    }
    @GetMapping("/admin/users/{id}")
    public ResponseEntity<?> getUsersById(@PathVariable String id){
        return usersService.getUsersById(id);
    }
    @GetMapping("/admin/users")
    public ResponseEntity<?> getAllUsers(){
        return usersService.getAllUsers();
    }
    @PutMapping("/admin/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UsersDto usersDto,@PathVariable String id){
        return usersService.updateUser(id, usersDto);
    }
    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<?> deleteUsersById(@PathVariable String id){
        return usersService.deleteUsersById(id);
    }
}
