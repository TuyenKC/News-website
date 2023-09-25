package com.newsproject.service.impl;

import com.newsproject.controller.dto.CommentDto;
import com.newsproject.controller.dto.ResponseObject;
import com.newsproject.repository.CommentRepository;
import com.newsproject.repository.NewsRepository;
import com.newsproject.repository.UsersRepository;
import com.newsproject.repository.entity.Comment;
import com.newsproject.repository.entity.News;
import com.newsproject.repository.entity.Users;
import com.newsproject.service.CommentService;
import com.newsproject.service.NewsService;
import com.newsproject.service.UsersService;

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
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<CommentDto> getCommentByNewsId(String newsId) {
        List<CommentDto> commentDtos = commentRepository.findAll().stream()
                .filter(comment -> newsId.equals(comment.getNews().getId()))
                .map(comment -> new EntityToDtoMapper().entityToDtoComment(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public ResponseEntity<?> getAllComment() {
        List<CommentDto> commentDtos = commentRepository.findAll().stream()
                .map(comment -> new EntityToDtoMapper().entityToDtoComment(comment)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Get All Comment successfully",commentDtos));
    }

    @Override
    public ResponseEntity<?> getCommentById(String id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if(!comment.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed","Can not find comment",""));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Get Comment successfully",new EntityToDtoMapper().entityToDtoComment(comment.get())));
    }

    @Override
    public ResponseEntity<?> addComment(CommentDto commentDto) {
        Optional<News> news = newsRepository.findById(commentDto.getNewsId());
        Optional<Users> users = usersRepository.findById(commentDto.getUsersId());
        Comment comment = new DtoToEntityMapper().dtoToEntityComment(commentDto, news.get(),users.get());
        Comment savedComment = commentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Add Comment successfully",new EntityToDtoMapper().entityToDtoComment(savedComment)));
    }

    @Override
    public ResponseEntity<?> updateComment(String id, CommentDto commentDto) {
        Optional<Comment> comment = commentRepository.findById(id);
        if(!comment.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed","Can not find comment",""));
        }
        Optional<News> news = newsRepository.findById(commentDto.getNewsId());
        Optional<Users> users = usersRepository.findById(commentDto.getUsersId());
        Comment updatedComment = commentRepository.save( new DtoToEntityMapper().dtoToEntityComment(commentDto,news.get(),users.get()));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Update Comment successfully",new EntityToDtoMapper().entityToDtoComment(updatedComment)));
    }

    @Override
    public ResponseEntity<?> deleteComment(String id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if(!comment.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("failed","Can not find comment",""));
        }
        commentRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Delete Comment successfully",""));
    }

}
