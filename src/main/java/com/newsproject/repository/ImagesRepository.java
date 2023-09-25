package com.newsproject.repository;

import com.newsproject.repository.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, String> {
}
