package com.newsproject.controller;

import com.newsproject.controller.dto.TopicsDto;
import com.newsproject.service.TopicsService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return topicsService.getTopicsById(id);
    }
    @PostMapping("/admin/topics")
    public ResponseEntity<?> addTopics(@RequestBody TopicsDto topicsDto){
        return topicsService.addTopics(topicsDto);
    }
    @PutMapping("/admin/topics/{id}")
    public ResponseEntity<?> updateTopics(@PathVariable String id,@RequestBody TopicsDto topicsDto){
        return topicsService.updatedTopics(id, topicsDto);
    }
    @DeleteMapping("/admin/topics/{id}")
    public ResponseEntity<?> deleteTopics(@PathVariable String id){
        return topicsService.deleteTopics(id);
    }
}
