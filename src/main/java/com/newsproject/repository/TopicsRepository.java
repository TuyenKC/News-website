package com.newsproject.repository;

import com.newsproject.repository.entity.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


public interface TopicsRepository extends JpaRepository<Topics, String> {
    boolean existByTitle(String title);
}
