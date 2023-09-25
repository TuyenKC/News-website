package com.newsproject.controller;

import com.newsproject.controller.dto.NewsDto;
import com.newsproject.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/admin/news")
    public ResponseEntity<?> getAllNews(){
        return newsService.getAllNews();
    }
    @GetMapping("/news/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable String id){
        return newsService.getNewsById(id);
    }
    @PostMapping("/admin/news")
    public ResponseEntity<?> addNews(@RequestBody NewsDto newsDto){
        return newsService.addNews(newsDto);
    }
    @PutMapping("/admin/news/{id}")
    public ResponseEntity<?> updateNews(@PathVariable String id, @RequestBody NewsDto newsDto){
        return newsService.updateNews(id, newsDto);
    }
    @DeleteMapping("/admin/news/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable String id){
        return newsService.deleteNews(id);
    }

}
