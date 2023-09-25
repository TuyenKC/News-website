package com.newsproject.service;

import com.newsproject.controller.dto.CommentDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    public List<CommentDto> getCommentByNewsId(String newsId);
    public ResponseEntity<?> getAllComment();
    public ResponseEntity<?> getCommentById(String id);
    public ResponseEntity<?> addComment(CommentDto commentDto);
    public ResponseEntity<?> updateComment(String id, CommentDto commentDto);
    public ResponseEntity<?> deleteComment(String id);
}
