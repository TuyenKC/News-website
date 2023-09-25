package com.newsproject.controller.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ImagesDto {
    private String id;
    private String title;
    private String extension;
    private String userId;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    private byte[] content;
}
