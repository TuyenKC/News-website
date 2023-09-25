package com.newsproject.service.impl;

import com.newsproject.controller.dto.ImagesDto;
import com.newsproject.controller.dto.ResponseObject;
import com.newsproject.repository.ImagesRepository;
import com.newsproject.repository.UsersRepository;
import com.newsproject.repository.entity.Images;
import com.newsproject.repository.entity.Users;
import com.newsproject.service.IStorageService;
import com.newsproject.service.ImagesService;
import com.newsproject.utils.DtoToEntityMapper;
import com.newsproject.utils.EntityToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImagesServiceImpl implements ImagesService {
    @Autowired
    private ImagesRepository imagesRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private IStorageService storageService;
    @Override
    public ResponseEntity<?> addImages(ImagesDto imagesDto) {
        Optional<Users> users = usersRepository.findById(imagesDto.getUserId());
        Images images = new DtoToEntityMapper().dtoToEntityImages(imagesDto,users.get());
        Images savedImages = imagesRepository.save(images);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Add Images successfully",new EntityToDtoMapper().entityToDtoImages(savedImages)));
    }

    @Override
    public ResponseEntity<?> getImagesByUsersId(String usersId) {
        List<ImagesDto> imagesDtoList = imagesRepository.findAll().stream()
                .filter(images -> images.getUsers().getId().equals(usersId))
                .map(images -> new EntityToDtoMapper().entityToDtoImages(images))
                .collect(Collectors.toList());
//        for(ImagesDto imagesDto: imagesDtoList){
//            byte[] content = (byte[]) storageService.readFileContent(imagesDto.getTitle()).getBody();
//            imagesDto.setContent(content);
//        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Get List Images successfully", imagesDtoList));
    }
}
