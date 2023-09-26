package com.newsproject.service.impl;

import com.newsproject.controller.dto.CommentDto;
import com.newsproject.controller.dto.ResponseObject;
import com.newsproject.exception.CommentNotExistedException;
import com.newsproject.exception.NewsNotExistedException;
import com.newsproject.exception.UsersNotExistedException;
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
    public List<CommentDto> getAllComment() {
        List<CommentDto> commentDtos = commentRepository.findAll().stream()
                .map(comment -> new EntityToDtoMapper().entityToDtoComment(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto getCommentById(String id) throws CommentNotExistedException {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CommentNotExistedException("Comment is not existed")
        );
        return new EntityToDtoMapper().entityToDtoComment(comment);
    }

    @Override
    public CommentDto addComment(CommentDto commentDto) throws NewsNotExistedException, UsersNotExistedException {
        News news = newsRepository.findById(commentDto.getNewsId()).orElseThrow(
                () -> new NewsNotExistedException("News is not existed")
        );
        Users users = usersRepository.findById(commentDto.getUsersId()).orElseThrow(
                () -> new UsersNotExistedException("Users is not existed")
        );
        Comment comment = new DtoToEntityMapper().dtoToEntityComment(commentDto, news,users);
        Comment savedComment = commentRepository.save(comment);
        return new EntityToDtoMapper().entityToDtoComment(savedComment);
    }

    @Override
    public CommentDto updateComment(String id, CommentDto commentDto) throws UsersNotExistedException, NewsNotExistedException, CommentNotExistedException {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CommentNotExistedException("Comment is not existed")
        );
        News news = newsRepository.findById(commentDto.getNewsId()).orElseThrow(
                () -> new NewsNotExistedException("News is not existed")
        );
        Users users = usersRepository.findById(commentDto.getUsersId()).orElseThrow(
                () -> new UsersNotExistedException("Users is not existed")
        );
        Comment updatedComment = commentRepository.save( new DtoToEntityMapper().dtoToEntityComment(commentDto,news,users));
        return new EntityToDtoMapper().entityToDtoComment(updatedComment);
    }

    @Override
    public void deleteComment(String id) throws CommentNotExistedException {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CommentNotExistedException("Comment is not existed")
        );
        commentRepository.deleteById(id);
    }

}
