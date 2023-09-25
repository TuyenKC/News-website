package com.newsproject.controller.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class NewsDto {
    private String id;
    private String title;
    private String content;
    private String slug;
    private String tags;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date modifiedAt;
    private String status;
    private Integer view;
    private String topicsId;
    private List<CommentDto> commentList;
}
