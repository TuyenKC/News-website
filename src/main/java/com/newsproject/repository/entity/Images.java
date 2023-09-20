package com.newsproject.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "images")
public class Images {
    @Id
    @Column(name = "images_id")
    private String id;
    @Column(name = "title")
    private String title;
    @Column(name = "created_at")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private Users users;
    @ManyToOne
    @JoinColumn(name = "news_id", nullable = false)
    private News news;


}
