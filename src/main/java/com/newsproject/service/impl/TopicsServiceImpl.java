package com.newsproject.service.impl;

import com.newsproject.controller.dto.ResponseObject;
import com.newsproject.controller.dto.TopicsDto;
import com.newsproject.repository.TopicsRepository;
import com.newsproject.repository.entity.Topics;
import com.newsproject.service.TopicsService;
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
    private TopicsRepository topicsRepository;
    @Override
    public ResponseEntity<?> addTopics(TopicsDto topicsDto) {
         Topics topics = dtoToEntity(topicsDto);
         Topics savedTopics = topicsRepository.save(topics);
         return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Add Topics successfully",savedTopics));
    }

    @Override
    public ResponseEntity<?> updatedTopics(String id, TopicsDto topicsDto) {
        Optional<Topics> topics = topicsRepository.findById(id);
        if(!topics.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed","Can not find Topics",""));
        Topics updatedTopics = topicsRepository.save(dtoToEntity(topicsDto));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Update Topics successfully",updatedTopics));
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
        List<TopicsDto> topicsDtos = topicsList.stream().map( topics -> entityToDto(topics)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Get All Topics successfully",topicsDtos));
    }

    @Override
    public ResponseEntity<?> getTopicsById(String id) {
        Optional<Topics> topics = topicsRepository.findById(id);
        if(!topics.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed","Can not find Topics",""));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Get Topics successfully",topics.get()));    }
    private Date getDateNow(){
        Date res = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date now = new Date();
        String dateNow = sdf.format(now);
        try{
            res = sdf.parse(dateNow);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
    private TopicsDto entityToDto(Topics topics){
        TopicsDto topicsDto = new TopicsDto();
        topicsDto.setTopicsId(topics.getId());
        topicsDto.setTitle(topics.getTitle());
        topicsDto.setCreatedAt(topics.getCreatedAt());
        topicsDto.setModifiedAt(topics.getModifiedAt());
        topicsDto.setTitle(topics.getTitle());
        return topicsDto;
    }
    private Topics dtoToEntity(TopicsDto topicsDto){
        Topics topics = new Topics();
        topics.setId(topicsDto.getTopicsId() != null ? topicsDto.getTopicsId() : new UUIDGenerator().generateUUID());
        topics.setTitle(topicsDto.getTitle() != null ? topicsDto.getTitle() : "");
        topics.setStatus(topicsDto.getStatus() != null ? topicsDto.getStatus() : "active");
        topics.setCreatedAt(topicsDto.getCreatedAt() != null ? topicsDto.getCreatedAt() : getDateNow());
        topics.setModifiedAt(getDateNow());
        return topics;
    }
}
