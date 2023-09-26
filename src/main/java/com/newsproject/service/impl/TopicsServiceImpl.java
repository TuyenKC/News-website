package com.newsproject.service.impl;

import com.newsproject.controller.dto.ResponseObject;
import com.newsproject.controller.dto.TopicsDto;
import com.newsproject.exception.TopicsNotExistedException;
import com.newsproject.exception.TopicsTitleExistedException;
import com.newsproject.repository.TopicsRepository;
import com.newsproject.repository.entity.Topics;
import com.newsproject.service.NewsService;
import com.newsproject.service.TopicsService;
import com.newsproject.utils.DtoToEntityMapper;
import com.newsproject.utils.EntityToDtoMapper;
import com.newsproject.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class TopicsServiceImpl implements TopicsService {
    @Autowired
    private  TopicsRepository topicsRepository;

    @Override
    public Optional<Topics> findById(String id) {
        return topicsRepository.findById(id);
    }

    @Override
    public TopicsDto addTopics(TopicsDto topicsDto) throws TopicsTitleExistedException {
         Topics topics = new DtoToEntityMapper().dtoToEntityTopics(topicsDto);
         if(topicsRepository.existByTitle(topics.getTitle())){
             throw new TopicsTitleExistedException("Topics Title is existed");
         }
         Topics savedTopics = topicsRepository.save(topics);
         return new EntityToDtoMapper().entityToDtoTopics(savedTopics);
    }

    @Override
    public TopicsDto updateTopics(String id, TopicsDto topicsDto) throws TopicsNotExistedException {
        Topics topics = topicsRepository.findById(id).orElseThrow( () -> new TopicsNotExistedException("Can not find topícs"));
        Topics updatedTopics = topicsRepository.save(new DtoToEntityMapper().dtoToEntityTopics(topicsDto));
        return new EntityToDtoMapper().entityToDtoTopics(updatedTopics);
    }

    @Override
    public void deleteTopics(String id) throws TopicsNotExistedException {
        Topics topics = topicsRepository.findById(id).orElseThrow( () -> new TopicsNotExistedException("Can not find topícs"));
        topicsRepository.deleteById(id);
    }

    @Override
    public List<TopicsDto> getAllTopics() {
        List<Topics> topicsList = topicsRepository.findAll();
        List<TopicsDto> topicsDtos = topicsList.stream().map( topics -> new EntityToDtoMapper().entityToDtoTopics(topics)).collect(Collectors.toList());
        return topicsDtos;
    }

    @Override
    public TopicsDto getTopicsById(String id) throws TopicsNotExistedException  {
        Topics topics = topicsRepository.findById(id).orElseThrow( () -> new TopicsNotExistedException("Can not find topícs"));
        return new EntityToDtoMapper().entityToDtoTopics(topics);
    }

}
