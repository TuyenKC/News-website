package com.newsproject.service.impl;

import com.newsproject.repository.RolesRepository;
import com.newsproject.repository.entity.ERole;
import com.newsproject.repository.entity.Roles;
import com.newsproject.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class RolesServiceImpl implements RolesService {
    @Autowired
    private RolesRepository rolesRepository;
    @Override
    public Optional<Roles> findByRoleName(ERole roleName) {
        return rolesRepository.findByRoleName(roleName);
    }
}
