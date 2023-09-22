package com.newsproject.service;



import com.newsproject.repository.entity.ERole;
import com.newsproject.repository.entity.Roles;

import java.util.Optional;

public interface RolesService {
    Optional<Roles> findByRoleName(ERole roleName);

}
