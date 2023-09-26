package com.newsproject.service;

import com.newsproject.controller.dto.CommentDto;
import com.newsproject.exception.CommentNotExistedException;
import com.newsproject.exception.NewsNotExistedException;
import com.newsproject.exception.UsersNotExistedException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    public List<CommentDto> getCommentByNewsId(String newsId);
    public List<CommentDto> getAllComment();
    public CommentDto getCommentById(String id) throws CommentNotExistedException;
    public CommentDto addComment(CommentDto commentDto) throws NewsNotExistedException, UsersNotExistedException;
    public CommentDto updateComment(String id, CommentDto commentDto) throws UsersNotExistedException, NewsNotExistedException, CommentNotExistedException;
    public void deleteComment(String id) throws CommentNotExistedException;
}
