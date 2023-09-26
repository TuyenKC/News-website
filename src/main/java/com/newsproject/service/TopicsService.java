package com.newsproject.service;

import com.newsproject.controller.dto.TopicsDto;
import com.newsproject.exception.TopicsNotExistedException;
import com.newsproject.exception.TopicsTitleExistedException;
import com.newsproject.repository.entity.Topics;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface TopicsService {
    public Optional<Topics> findById(String id);
    public TopicsDto addTopics(TopicsDto topicsDto) throws TopicsTitleExistedException;
    public TopicsDto updateTopics(String id, TopicsDto topicsDto) throws TopicsNotExistedException;
    public void deleteTopics(String id) throws TopicsNotExistedException;
    public List<TopicsDto> getAllTopics();
    public TopicsDto getTopicsById(String id) throws TopicsNotExistedException;

}
