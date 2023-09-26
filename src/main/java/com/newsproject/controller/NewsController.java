package com.newsproject.controller;

import com.newsproject.controller.dto.NewsDto;
import com.newsproject.controller.dto.ResponseObject;
import com.newsproject.controller.dto.TopicsDto;
import com.newsproject.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/admin/news")
    public ResponseEntity<?> getAllNews(){
        try{
            newsService.getAllNews();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Get All News successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @GetMapping("/news/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable String id){
        try{
            newsService.getNewsById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Get News successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @PostMapping("/admin/news")
    public ResponseEntity<?> addNews(@RequestBody NewsDto newsDto){
        try{
            newsService.addNews(newsDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Add News successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @PutMapping("/admin/news/{id}")
    public ResponseEntity<?> updateNews(@PathVariable String id, @RequestBody NewsDto newsDto){
        try{
            newsService.updateNews(id, newsDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Update news successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @DeleteMapping("/admin/news/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable String id){
        try{
            newsService.deleteNews(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Delete news successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }

}
