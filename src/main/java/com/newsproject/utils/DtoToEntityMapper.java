package com.newsproject.utils;

import com.newsproject.controller.dto.*;

import com.newsproject.repository.NewsRepository;
import com.newsproject.repository.TopicsRepository;
import com.newsproject.repository.entity.*;
import com.newsproject.service.NewsService;
import com.newsproject.service.RolesService;
import com.newsproject.service.TopicsService;
import com.newsproject.service.impl.NewsServiceImpl;
import com.newsproject.service.impl.RolesServiceImpl;
import com.newsproject.service.impl.TopicsServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class DtoToEntityMapper {
    private PasswordEncoder passwordEncoder ;
    private RolesService rolesService ;



    public Comment dtoToEntityComment(CommentDto commentDto, News news , Users users){
        Comment comment = new Comment();
        comment.setId(commentDto.getId() != null ? comment.getId() : new UUIDGenerator().generateUUID());
        comment.setContent(commentDto.getContent());
        comment.setStatus(commentDto.getStatus() != null ? commentDto.getStatus() : "active" );
        comment.setCreatedAt(commentDto.getCreatedAt() != null ? commentDto.getCreatedAt(): GetDateNow.getDateNow());
        comment.setModifiedAt(GetDateNow.getDateNow());
        comment.setNews(news);
        comment.setUsers(users);
        return comment;
    }
    public Topics dtoToEntityTopics(TopicsDto topicsDto){
        Topics topics = new Topics();
        topics.setId(topicsDto.getTopicsId() != null ? topicsDto.getTopicsId() : new UUIDGenerator().generateUUID());
        topics.setTitle(topicsDto.getTitle() != null ? topicsDto.getTitle() : "");
        topics.setStatus(topicsDto.getStatus() != null ? topicsDto.getStatus() : "active");
        topics.setCreatedAt(topicsDto.getCreatedAt() != null ? topicsDto.getCreatedAt() : GetDateNow.getDateNow());
        topics.setModifiedAt(GetDateNow.getDateNow());
        return topics;
    }
    public News dtoToEntityNews(NewsDto newsDto, Topics topics){
        News news = new News();
        news.setId(newsDto.getId() != null ? newsDto.getId() : new UUIDGenerator().generateUUID());
        news.setCreatedAt(newsDto.getCreatedAt() != null ? newsDto.getCreatedAt() : GetDateNow.getDateNow());
        news.setModifiedAt(GetDateNow.getDateNow());
        news.setTitle(newsDto.getTitle() != null ? newsDto.getTitle() : "");
        news.setContent(newsDto.getContent() != null ? newsDto.getContent() : "");
        news.setStatus(newsDto.getStatus());
        news.setSlug(newsDto.getSlug() != null ? newsDto.getSlug() : "");
        news.setTags(newsDto.getTags() != null ? newsDto.getTags() : "");
        news.setView(news.getView() != null ? newsDto.getView() + 1 : 1);
        news.setTopics(topics);
        return news;
    }
    public Users dtoToEntityUsers(UsersDto usersDto){
        Users users = new Users();
        users.setId(usersDto.getUserId() != null ? usersDto.getUserId() : new UUIDGenerator().generateUUID());
        users.setStatus(usersDto.isStatus());
        String encodedPassword = passwordEncoder.encode(usersDto.getPassword());
        users.setUsername(usersDto.getUsername());
        users.setPassword(encodedPassword);
        users.setEmail(usersDto.getEmail() != null ? usersDto.getEmail() :  "");
        users.setPhone(usersDto.getPhone() != null ? usersDto.getPhone() :  "");
        users.setAddress(usersDto.getAddress() != null ? usersDto.getAddress() :  "");
        users.setCreatedAt(usersDto.getCreatedAt() != null ? usersDto.getCreatedAt() : GetDateNow.getDateNow());
        users.setModifiedAt(GetDateNow.getDateNow());
        users.setRolesSet(getRolesSetFromString(usersDto.getListRoles()));
        return users;
    }
    public Images dtoToEntityImages(ImagesDto imagesDto, Users users){
        Images images = new Images();
        images.setId(imagesDto.getId() != null ? imagesDto.getId() : new UUIDGenerator().generateUUID());
        images.setTitle(imagesDto.getTitle());
        images.setCreatedAt(imagesDto.getCreatedAt() != null ? imagesDto.getCreatedAt() : GetDateNow.getDateNow());
        images.setExtension(imagesDto.getExtension());
        images.setUsers(users);
        return images;
    }
    public Set<Roles> getRolesSetFromString(Set<String> strRoles) {
        Set<Roles> rolesSet = new HashSet<>();
        if (strRoles == null) {
            Roles userRole = rolesService.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            rolesSet.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = rolesService.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        rolesSet.add(adminRole);
                    case "moderator":
                        Roles moderatorRole = rolesService.findByRoleName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        rolesSet.add(moderatorRole);
                    case "user":
                        Roles userRole = rolesService.findByRoleName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        rolesSet.add(userRole);
                }
            });

        }
        return rolesSet;
    }

}
