package com.newsproject.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "topics")
@Data
public class Topics {
    @Id
    @Column(name = "topics_id")
    private String id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "created_at")
    @JsonFormat(pattern =  "dd/MM/yyyy")
    private Date createdAt;
    @Column(name = "modified_at")
    @JsonFormat(pattern =  "dd/MM/yyyy")
    private Date modifiedAt;
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "topics")
    private List<News> newsList;


}
