package com.newsproject.controller;

import com.newsproject.controller.dto.ImagesDto;
import com.newsproject.controller.dto.ResponseObject;
import com.newsproject.service.IStorageService;
import com.newsproject.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        try{
            storageService.storeFile(file,usersId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Upload file successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @PostMapping("")
    public ResponseEntity<?> addImages(@RequestBody ImagesDto imagesDto){
        try{
            imagesService.addImages(imagesDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Add Images successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @GetMapping("/users/{usersId}")
    public ResponseEntity<?> getImageByUsersId(@PathVariable String usersId){
        try{
            imagesService.getImagesByUsersId(usersId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Get Images successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @GetMapping("")
    public ResponseEntity<?> getAllImage(){
        try{
            storageService.loadAll();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Load all successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<?> readFile(@PathVariable String fileName){
        try{
            storageService.readFileContent(fileName);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","read file successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
}
