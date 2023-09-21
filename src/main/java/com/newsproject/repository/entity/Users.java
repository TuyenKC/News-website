package com.newsproject.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Users {
    @Id
    @Column(name = "users_id")
    private String id;
    @Column(name = "username", nullable = false , unique = true)
    private String username;
    @Column(name = "password" , nullable = false, length = 15)
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "created_at")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    @Column(name = "modified_at")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date modifiedAt;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ERole role;
    @Column(name = "status")
    private boolean status;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "roleId"), inverseJoinColumns = @JoinColumn(name = "userId"))
    private Set<Roles> rolesSet;
    @OneToMany(mappedBy = "users")
    private List<Comment> commentList;
    @OneToMany(mappedBy = "users")
    private List<Images> imagesList;
}
