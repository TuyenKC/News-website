package com.newsproject.service;

import com.newsproject.controller.dto.ImagesDto;
import org.springframework.http.ResponseEntity;

public interface ImagesService {
    public ResponseEntity<?> addImages(ImagesDto imagesDto);
    public ResponseEntity<?> getImagesByUsersId(String usersId);
}
