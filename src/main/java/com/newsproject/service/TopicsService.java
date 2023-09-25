package com.newsproject.service;

import com.newsproject.controller.dto.TopicsDto;
import com.newsproject.repository.entity.Topics;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface TopicsService {
    public Optional<Topics> findById(String id);
    public ResponseEntity<?> addTopics(TopicsDto topicsDto);
    public ResponseEntity<?> updateTopics(String id, TopicsDto topicsDto);
    public ResponseEntity<?> deleteTopics(String id);
    public ResponseEntity<?> getAllTopics();
    public ResponseEntity<?> getTopicsById(String id);

}
