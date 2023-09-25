package com.newsproject.controller;

import com.newsproject.controller.dto.CommentDto;
import com.newsproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/admin/comment")
    public ResponseEntity<?> getAllComment(){
        return commentService.getAllComment();
    }
    @GetMapping("/admin/comment/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable String id){
        return commentService.getCommentById(id);
    }
    @PostMapping("/admin/comment")
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto){
        return commentService.addComment(commentDto);
    }
    @PutMapping("/admin/comment/{id}")
    public ResponseEntity<?> updateComment(@PathVariable String id,@RequestBody CommentDto commentDto){
        return commentService.updateComment(id, commentDto);
    }
    @DeleteMapping("/admin/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable String id){
        return commentService.deleteComment(id);
    }
}
