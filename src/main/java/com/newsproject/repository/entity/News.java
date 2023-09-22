package com.newsproject.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "news")
@Data
public class News {
    @Id
    @Column(name = "news_id")
    private String id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "slug")
    private String slug;
    @Column(name = "tags")
    private String tags;
    @Column(name = "created_at")
    @JsonFormat(pattern =  "dd/MM/yyyy")
    private Date createdAt;
    @Column(name = "modified_at")
    @JsonFormat(pattern =  "dd/MM/yyyy")
    private Date modifiedAt;
    @Column(name = "view")
    private Integer view;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EStatus status;

    @ManyToOne
    @JoinColumn(name = "topics_id")
    private Topics topics;
    @OneToMany(mappedBy = "news")
    private List<Images> imagesList;
}
