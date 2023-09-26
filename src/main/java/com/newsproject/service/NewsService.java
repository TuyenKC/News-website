package com.newsproject.service;

import com.newsproject.controller.dto.NewsDto;
import com.newsproject.exception.NewsExistedException;
import com.newsproject.exception.NewsNotExistedException;
import com.newsproject.exception.TopicsNotExistedException;
import com.newsproject.repository.entity.News;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface NewsService {
    public Optional<News> findById(String id);
    public List<NewsDto> getNewsByTopicsId(String topicsId);
    public List<NewsDto> getAllNews();
    public NewsDto getNewsById(String id) throws NewsNotExistedException;
    public NewsDto addNews(NewsDto newsDto) throws NewsExistedException, TopicsNotExistedException;
    public NewsDto updateNews(String id, NewsDto newsDto) throws NewsNotExistedException, TopicsNotExistedException;
    public void deleteNews(String id) throws NewsNotExistedException;
    public List<NewsDto> getListMostViewNews();

}
