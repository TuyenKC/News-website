package com.newsproject.controller;

import com.newsproject.controller.dto.ResponseObject;
import com.newsproject.controller.dto.TopicsDto;
import com.newsproject.exception.TopicsNotExistedException;
import com.newsproject.service.TopicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TopicsController {
    @Autowired
    private TopicsService topicsService;

    @GetMapping("/admin/topics")
    public ResponseEntity<?> getAllTopics(){
        return topicsService.getAllTopics();
    }
    @GetMapping("/admin/topics/{id}")
    public ResponseEntity<?> getTopicsById(@PathVariable String id){
        try{
            TopicsDto topicsDto = topicsService.getTopicsById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Get Topics successfully",topicsDto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @PostMapping("/admin/topics")
    public ResponseEntity<?> addTopics(@RequestBody TopicsDto topicsDto){
        try{
            topicsService.addTopics(topicsDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Add Topics successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @PutMapping("/admin/topics/{id}")
    public ResponseEntity<?> updateTopics(@PathVariable String id,@RequestBody TopicsDto topicsDto){
        try{
            topicsService.updateTopics(id, topicsDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Updated Topics successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @DeleteMapping("/admin/topics/{id}")
    public ResponseEntity<?> deleteTopics(@PathVariable String id){
        try{
            topicsService.deleteTopics(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Delete Topics successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
}
