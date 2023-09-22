package com.newsproject.service;

import com.newsproject.controller.dto.TopicsDto;
import org.springframework.http.ResponseEntity;

public interface TopicsService {
    public ResponseEntity<?> addTopics(TopicsDto topicsDto);
    public ResponseEntity<?> updatedTopics(String id, TopicsDto topicsDto);
    public ResponseEntity<?> deleteTopics(String id);
    public ResponseEntity<?> getAllTopics();
    public ResponseEntity<?> getTopicsById(String id);

}
