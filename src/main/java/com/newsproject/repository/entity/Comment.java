package com.newsproject.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name = "comment_id")
    private String id;
    @Column(name = "content")
    private String content;
    @Column(name = "created_at")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    @Column(name = "modified_at")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date modifiedAt;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EStatus status;
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private Users users;


}
