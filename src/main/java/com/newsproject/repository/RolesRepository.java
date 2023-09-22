package com.newsproject.repository;

import com.newsproject.repository.entity.ERole;
import com.newsproject.repository.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles,Integer> {
    Optional<Roles> findByRoleName(ERole roleName);

}
