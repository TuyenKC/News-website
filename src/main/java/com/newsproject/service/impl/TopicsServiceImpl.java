package com.newsproject.service.impl;

import com.newsproject.controller.dto.ResponseObject;
import com.newsproject.controller.dto.TopicsDto;
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
    public ResponseEntity<?> addTopics(TopicsDto topicsDto) {
         Topics topics = new DtoToEntityMapper().dtoToEntityTopics(topicsDto);
         Topics savedTopics = topicsRepository.save(topics);
         return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Add Topics successfully",new EntityToDtoMapper().entityToDtoTopics(savedTopics)));
    }

    @Override
    public ResponseEntity<?> updateTopics(String id, TopicsDto topicsDto) {
        Optional<Topics> topics = topicsRepository.findById(id);
        if(!topics.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed","Can not find Topics",""));
        Topics updatedTopics = topicsRepository.save(new DtoToEntityMapper().dtoToEntityTopics(topicsDto));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Update Topics successfully",new EntityToDtoMapper().entityToDtoTopics(updatedTopics)));
    }

    @Override
    public ResponseEntity<?> deleteTopics(String id) {
        Optional<Topics> topics = topicsRepository.findById(id);
        if(!topics.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed","Can not find Topics",""));
        topicsRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Delete Topics successfully",""));
    }

    @Override
    public ResponseEntity<?> getAllTopics() {
        List<Topics> topicsList = topicsRepository.findAll();
        List<TopicsDto> topicsDtos = topicsList.stream().map( topics -> new EntityToDtoMapper().entityToDtoTopics(topics)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Get All Topics successfully",topicsDtos));
    }

    @Override
    public ResponseEntity<?> getTopicsById(String id) {
        Optional<Topics> topics = topicsRepository.findById(id);
        if(!topics.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed","Can not find Topics",""));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Get Topics successfully",new EntityToDtoMapper().entityToDtoTopics(topics.get())));    }


}
