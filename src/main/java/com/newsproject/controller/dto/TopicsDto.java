package com.newsproject.controller.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class TopicsDto {
    private String topicsId;
    private String title;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date modifiedAt;
    private String status;
    private List<NewsDto> newsList;

}
