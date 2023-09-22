package com.newsproject.controller.dto;

import com.newsproject.utils.UUIDGenerator;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
@Data
public class UsersDto {
    private String userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date modifiedAt;
    private boolean status;
    private Set<String> listRoles;

}
