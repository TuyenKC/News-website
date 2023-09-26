package com.newsproject.controller;

import com.newsproject.controller.dto.ResponseObject;
import com.newsproject.controller.dto.UsersDto;
import com.newsproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UsersService usersService;
    @PostMapping("/admin/users")
    public ResponseEntity<?> addUser(@RequestBody UsersDto usersDto){
        try{
            usersService.addUser(usersDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Add Users successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @GetMapping("/admin/users/{id}")
    public ResponseEntity<?> getUsersById(@PathVariable String id){
        try{
            usersService.getUsersById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Add Users successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @GetMapping("/admin/users")
    public ResponseEntity<?> getAllUsers(){
        try{
            usersService.getAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Add Users successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @PutMapping("/admin/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UsersDto usersDto,@PathVariable String id){
        try{
            usersService.updateUser(id, usersDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Add Users successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<?> deleteUsersById(@PathVariable String id){
        try{
            usersService.deleteUsersById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Add Users successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
}
