package com.newsproject.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.newsproject.utils.UUIDGenerator;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
@Data
public class SignUpDto {
    private String username;
    private String password;
    private Set<String> listRoles;
}
