package com.newsproject.controller;

import com.newsproject.controller.dto.CommentDto;
import com.newsproject.controller.dto.ResponseObject;
import com.newsproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/admin/comment")
    public ResponseEntity<?> getAllComment(){
        try{
            commentService.getAllComment();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Load all comments successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @GetMapping("/admin/comment/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable String id){
        try{
            commentService.getCommentById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Get comment successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @PostMapping("/admin/comment")
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto){
        try{
            commentService.addComment(commentDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Add comment successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @PutMapping("/admin/comment/{id}")
    public ResponseEntity<?> updateComment(@PathVariable String id,@RequestBody CommentDto commentDto){
        try{
            commentService.updateComment(id, commentDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Update comment successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
    @DeleteMapping("/admin/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable String id){
        try{
            commentService.deleteComment(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Delete comment successfully",""));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed",e.getMessage(),""));
        }
    }
}
