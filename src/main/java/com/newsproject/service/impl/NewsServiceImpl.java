package com.newsproject.service.impl;

import com.newsproject.controller.dto.NewsDto;
import com.newsproject.controller.dto.ResponseObject;
import com.newsproject.exception.NewsExistedException;
import com.newsproject.exception.NewsNotExistedException;
import com.newsproject.exception.TopicsNotExistedException;
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
    public List<NewsDto> getAllNews() {
        List<NewsDto> newsListDtos = newsRepository.findAll().stream().map(
                news -> new EntityToDtoMapper().entityToDtoNews(news)
        ).collect(Collectors.toList());
        return newsListDtos;
    }

    @Override
    public NewsDto getNewsById(String id) throws NewsNotExistedException {
        News news = newsRepository.findById(id).orElseThrow(
                () -> new NewsNotExistedException("News is not existed")
        );
        return new EntityToDtoMapper().entityToDtoNews(news);
    }

    @Override
    public NewsDto addNews(NewsDto newsDto) throws TopicsNotExistedException {
        Topics topics = topicsRepository.findById(newsDto.getTopicsId()).orElseThrow( () -> new TopicsNotExistedException("Can not find topics"));
        News news = new DtoToEntityMapper().dtoToEntityNews(newsDto,topics);
        News savedNews = newsRepository.save(news);
        return new EntityToDtoMapper().entityToDtoNews(savedNews);
    }

    @Override
    public NewsDto updateNews(String id,NewsDto newsDto) throws NewsNotExistedException, TopicsNotExistedException {
        News news = newsRepository.findById(id).orElseThrow(
                () -> new NewsNotExistedException("News is not existed")
        );
        Topics topics = topicsRepository.findById(newsDto.getTopicsId()).orElseThrow( () -> new TopicsNotExistedException("Can not find topics"));
        News savedNews = newsRepository.save(new DtoToEntityMapper().dtoToEntityNews(newsDto , topics));
        return new EntityToDtoMapper().entityToDtoNews(savedNews);
    }

    @Override
    public void deleteNews(String id) throws NewsNotExistedException {
        News news = newsRepository.findById(id).orElseThrow(
                () -> new NewsNotExistedException("News is not existed")
        );
        newsRepository.deleteById(id);
    }

    @Override
    public List<NewsDto> getListMostViewNews() {
        return null;
    }

  
}
