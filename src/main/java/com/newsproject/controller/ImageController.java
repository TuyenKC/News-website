package com.newsproject.controller;

import com.newsproject.controller.dto.ImagesDto;
import com.newsproject.service.IStorageService;
import com.newsproject.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    @Autowired
    private IStorageService storageService;
    @Autowired
    private ImagesService imagesService;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file, @RequestParam("usersId") String usersId){
        return storageService.storeFile(file,usersId);
    }
    @PostMapping("")
    public ResponseEntity<?> addImages(@RequestBody ImagesDto imagesDto){
        return imagesService.addImages(imagesDto);
    }
    @GetMapping("/users/{usersId}")
    public ResponseEntity<?> getImageByUsersId(@PathVariable String usersId){
        return imagesService.getImagesByUsersId(usersId);
    }
    @GetMapping("")
    public ResponseEntity<?> getAllImage(){
        return storageService.loadAll();
    }
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<?> readFile(@PathVariable String fileName){
        return storageService.readFileContent(fileName);
    }
}
