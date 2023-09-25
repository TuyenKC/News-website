package com.newsproject.utils;

import com.newsproject.controller.dto.*;
import com.newsproject.repository.entity.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class EntityToDtoMapper {
    public CommentDto entityToDtoComment(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setModifiedAt(comment.getModifiedAt());
        commentDto.setStatus(comment.getStatus());
        commentDto.setUsersId(comment.getUsers().getId());
        return commentDto;
    }
    public NewsDto entityToDtoNews(News news){
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setContent(news.getContent());
        newsDto.setCreatedAt(news.getCreatedAt());
        newsDto.setModifiedAt(news.getModifiedAt());
        newsDto.setStatus(news.getStatus());
        newsDto.setTitle(news.getTitle());
        newsDto.setSlug(news.getSlug());
        newsDto.setTags(news.getTags());
        newsDto.setView(news.getView());
        newsDto.setCommentList(news.getComment() != null ? news.getComment().stream()
                .map(comment -> entityToDtoComment(comment)).collect(Collectors.toList()) : new ArrayList<CommentDto>());
        return newsDto;
    }
    public TopicsDto entityToDtoTopics(Topics topics){
        TopicsDto topicsDto = new TopicsDto();
        topicsDto.setTopicsId(topics.getId());
        topicsDto.setTitle(topics.getTitle());
        topicsDto.setCreatedAt(topics.getCreatedAt());
        topicsDto.setModifiedAt(topics.getModifiedAt());
        topicsDto.setTitle(topics.getTitle());
        topicsDto.setNewsList(topics.getNewsList() != null ? topics.getNewsList().stream()
                .map(news -> entityToDtoNews(news)).collect(Collectors.toList()) : new ArrayList<NewsDto>());
        return topicsDto;
    }
    public UsersDto entityToDtoUsers(Users users){
        UsersDto usersDto = new UsersDto();
        usersDto.setUserId(users.getId());
        usersDto.setUsername(users.getUsername());
        usersDto.setPassword(users.getPassword());
        usersDto.setEmail(users.getEmail());
        usersDto.setPhone(users.getPhone());
        usersDto.setStatus(users.isStatus());
        usersDto.setCreatedAt(users.getCreatedAt());
        usersDto.setModifiedAt(users.getModifiedAt());
        usersDto.setListRoles(users.getRolesSet().stream().map(role -> role.getRoleName().toString()).collect(Collectors.toSet()));
        return usersDto;
    }
    public ImagesDto entityToDtoImages(Images images){
        ImagesDto imagesDto = new ImagesDto();
        imagesDto.setId(images.getId());
        imagesDto.setTitle(images.getTitle());
        imagesDto.setCreatedAt(images.getCreatedAt());
        imagesDto.setExtension(images.getExtension());
        imagesDto.setUserId(images.getUsers().getId());
        return imagesDto;
    }
}
