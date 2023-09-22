package com.newsproject.controller.dto;

import lombok.Data;

import java.util.List;
@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String userId;
    private List<String> listRoles;

    public JwtResponse(String token,  String userId, List<String> listRoles) {
        this.token = token;
        this.userId = userId;
        this.listRoles = listRoles;
    }

    public JwtResponse(String token, List<String> listRoles) {
        this.token = token;
        this.listRoles = listRoles;
    }

}
