package com.newsproject.service.impl;

import com.newsproject.controller.dto.*;
import com.newsproject.exception.UsernameValidateException;
import com.newsproject.exception.UsersNotExistedException;
import com.newsproject.repository.UsersRepository;
import com.newsproject.repository.entity.ERole;
import com.newsproject.repository.entity.Roles;
import com.newsproject.repository.entity.Users;
import com.newsproject.security.CustomUserDetails;
import com.newsproject.service.RolesService;
import com.newsproject.service.UsersService;
import com.newsproject.utils.DtoToEntityMapper;
import com.newsproject.utils.EntityToDtoMapper;
import com.newsproject.utils.JwtTokenProvider;
import com.newsproject.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RolesService rolesService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsersRepository usersRepository;
    @Override
    public Users findByUserName(String userName) {
        return usersRepository.findByUsername(userName);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return usersRepository.existsByUsername(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    @Override
    public Users saveOrUpdate(Users user) {
        return usersRepository.save(user);
    }

    @Override
    public UsersDto addUser(UsersDto usersDto) throws UsernameValidateException {
        if(usersRepository.existsByUsername(usersDto.getUsername())){
            throw new UsernameValidateException("Username is existed");
        }
        Users users = new DtoToEntityMapper().dtoToEntityUsers(usersDto);
        Users savedUsers = usersRepository.save(users);
        return new EntityToDtoMapper().entityToDtoUsers(savedUsers);
    }

    @Override
    public UsersDto getUsersById(String id) throws UsersNotExistedException {
        Users users = usersRepository.findById(id).orElseThrow(
                () -> new UsersNotExistedException("Users is not existed")
        );
        return new EntityToDtoMapper().entityToDtoUsers(users);
    }

    @Override
    public List<UsersDto> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        List<UsersDto> usersDtos = users.stream().map( user -> new EntityToDtoMapper().entityToDtoUsers(user) ).collect(Collectors.toList());
        return usersDtos;

    }

    @Override
    public void deleteUsersById(String id) throws UsersNotExistedException {
        Users users = usersRepository.findById(id).orElseThrow(
                () -> new UsersNotExistedException("Users is not existed")
        );
        usersRepository.deleteById(id);
    }

    @Override
    public UsersDto updateUser(String id, UsersDto usersDto) throws UsersNotExistedException {
        Users usersCheck = usersRepository.findById(id).orElseThrow(
                () -> new UsersNotExistedException("Users is not existed")
        );
        Users users = new DtoToEntityMapper().dtoToEntityUsers(usersDto);
        Users savedUsers = usersRepository.save(users);
        return new EntityToDtoMapper().entityToDtoUsers(savedUsers);
    }

    public void signUpUser(SignUpDto signUpDto) throws UsernameValidateException {
        if(usersRepository.existsByUsername(signUpDto.getUsername())){
            throw new UsernameValidateException("Username is existed");
        }
        Users users = new Users();
        users.setId(new UUIDGenerator().generateUUID());
        users.setUsername(signUpDto.getUsername());
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        users.setPassword(encodedPassword);
        users.setStatus(true);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date now = new Date();
        String dateNow = sdf.format(now);
        try{
            users.setCreatedAt(sdf.parse(dateNow));
            users.setModifiedAt(sdf.parse(dateNow));
        }catch (Exception e){
            e.printStackTrace();
        }
        Set<String> strRoles = signUpDto.getListRoles();
        Set<Roles> rolesSet = getRolesSetFromString(strRoles);
        users.setRolesSet(rolesSet);
        usersRepository.save(users);
    }
    public JwtResponse logInUser(LogInDto loginDto){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwt = jwtTokenProvider.generateToken(customUserDetails);
            List<String> listRoles = customUserDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority()).collect(Collectors.toList());
            return new JwtResponse(jwt, customUserDetails.getUsername(), listRoles);
        }catch (AuthenticationException e){
            throw e;
        }

    }
    private Set<Roles> getRolesSetFromString(Set<String> strRoles) {
        Set<Roles> rolesSet = new HashSet<>();
        if (strRoles == null) {
            Roles userRole = rolesService.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            rolesSet.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = rolesService.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        rolesSet.add(adminRole);
                    case "moderator":
                        Roles moderatorRole = rolesService.findByRoleName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        rolesSet.add(moderatorRole);
                    case "user":
                        Roles userRole = rolesService.findByRoleName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        rolesSet.add(userRole);
                }
            });

        }
        return rolesSet;
    }


}
