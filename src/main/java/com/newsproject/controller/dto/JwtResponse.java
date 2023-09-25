package com.newsproject.controller.dto;

import lombok.Data;

import java.util.List;
@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private List<String> listRoles;

    public JwtResponse(String token,  String username, List<String> listRoles) {
        this.token = token;
        this.username = username;
        this.listRoles = listRoles;
    }

    public JwtResponse(String token, List<String> listRoles) {
        this.token = token;
        this.listRoles = listRoles;
    }

}
