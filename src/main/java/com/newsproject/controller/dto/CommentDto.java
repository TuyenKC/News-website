package com.newsproject.controller.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CommentDto {
    private String id;
    private String content;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date modifiedAt;
    private String status;
    private String newsId;
    private String usersId;

}
