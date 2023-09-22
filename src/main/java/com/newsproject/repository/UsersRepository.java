package com.newsproject.repository;

import com.newsproject.repository.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,String> {
    public Users findByUsername(String userName);
    boolean existsByUsername(String userName);
    boolean existsByEmail(String email);
}
