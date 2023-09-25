package com.newsproject.service.impl;

import com.newsproject.controller.dto.NewsDto;
import com.newsproject.controller.dto.ResponseObject;
import com.newsproject.repository.NewsRepository;
import com.newsproject.repository.TopicsRepository;
import com.newsproject.repository.entity.News;
import com.newsproject.repository.entity.Topics;
import com.newsproject.service.CommentService;
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
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private TopicsRepository topicsRepository;
    @Override
    public Optional<News> findById(String id) {
        return newsRepository.findById(id);
    }

    @Override
    public List<NewsDto> getNewsByTopicsId(String topicsId) {
        List<NewsDto> newsListDtos = newsRepository.findAll().stream()
                .filter(news -> topicsId.equals(news.getTopics().getId()))
                .map(news -> new EntityToDtoMapper().entityToDtoNews(news)
                ).collect(Collectors.toList());
        return newsListDtos;
    }

    @Override
    public ResponseEntity<?> getAllNews() {
        List<NewsDto> newsListDtos = newsRepository.findAll().stream().map(
                news -> new EntityToDtoMapper().entityToDtoNews(news)
        ).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Get All News successfully",newsListDtos));
    }

    @Override
    public ResponseEntity<?> getNewsById(String id) {
        Optional<News> news = newsRepository.findById(id);
        if(!news.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed","Can not find News",""));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Get  News successfully",new EntityToDtoMapper().entityToDtoNews(news.get())));
    }

    @Override
    public ResponseEntity<?> addNews(NewsDto newsDto) {
        Optional<Topics> topics = topicsRepository.findById(newsDto.getTopicsId());
        News news = new DtoToEntityMapper().dtoToEntityNews(newsDto,topics.get());
        News savedNews = newsRepository.save(news);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Add News successfully", new EntityToDtoMapper().entityToDtoNews(savedNews)));
    }

    @Override
    public ResponseEntity<?> updateNews(String id,NewsDto newsDto) {
        Optional<News> news = newsRepository.findById(id);
        if(!news.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed","Can not find News",""));
        }
        Optional<Topics> topics = topicsRepository.findById(newsDto.getTopicsId());
        News savedNews = newsRepository.save(new DtoToEntityMapper().dtoToEntityNews(newsDto , topics.get()));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Update News successfully", new EntityToDtoMapper().entityToDtoNews(savedNews)));
    }

    @Override
    public ResponseEntity<?> deleteNews(String id) {
        Optional<News> news = newsRepository.findById(id);
        if(!news.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed","Can not find News",""));
        }

        newsRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Delete News successfully",""));
    }

    @Override
    public ResponseEntity<?> getListMostViewNews() {
        return null;
    }

  
}
