package com.newsproject.service;

import com.newsproject.controller.dto.ImagesDto;
import com.newsproject.exception.UsersNotExistedException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ImagesService {
    public ImagesDto addImages(ImagesDto imagesDto) throws UsersNotExistedException;
    public List<ImagesDto> getImagesByUsersId(String usersId);
}
