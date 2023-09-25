package com.newsproject.service;

import com.newsproject.controller.dto.NewsDto;
import com.newsproject.repository.entity.News;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface NewsService {
    public Optional<News> findById(String id);
    public List<NewsDto> getNewsByTopicsId(String topicsId);
    public ResponseEntity<?> getAllNews();
    public ResponseEntity<?> getNewsById(String id);
    public ResponseEntity<?> addNews(NewsDto newsDto);
    public ResponseEntity<?> updateNews(String id, NewsDto newsDto);
    public ResponseEntity<?> deleteNews(String id);
    public ResponseEntity<?> getListMostViewNews();

}
