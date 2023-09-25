package com.newsproject.service.impl;

import com.newsproject.controller.dto.*;
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
    public ResponseEntity<?> addUser(UsersDto usersDto) {
        if(usersRepository.existsByUsername(usersDto.getUsername())){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed","Error: Username is existed", ""));
        }
        Users users = new DtoToEntityMapper().dtoToEntityUsers(usersDto);
        System.out.println(users);
        usersRepository.save(users);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Add User successfully",""));
    }

    @Override
    public ResponseEntity<?> getUsersById(String id) {
        Optional<Users> users = usersRepository.findById(id);
        if(!users.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed","Can not find user",""));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Get Users successfully",new EntityToDtoMapper().entityToDtoUsers(users.get())));
    }

    @Override
    public ResponseEntity<?> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        List<UsersDto> usersDtos = users.stream().map( user -> new EntityToDtoMapper().entityToDtoUsers(user) ).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Get All Users successfully",usersDtos));

    }

    @Override
    public ResponseEntity<?> deleteUsersById(String id) {
        Optional<Users> users = usersRepository.findById(id);
        if(!users.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed","Can not find user",""));
        usersRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Delete Users Successfully",""));
    }

    @Override
    public ResponseEntity<?> updateUser(String id, UsersDto usersDto) {
        Users users = new DtoToEntityMapper().dtoToEntityUsers(usersDto);
        usersRepository.save(users);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","Update User successfully",new EntityToDtoMapper().entityToDtoUsers(users)));
    }

    public ResponseEntity<?> signUpUser(SignUpDto signUpDto){
        if(usersRepository.existsByUsername(signUpDto.getUsername())){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed","Error: Username is existed", ""));
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
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success","User registered successfully",""));
    }
    public ResponseEntity<?> logInUser(LogInDto loginDto){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwt = jwtTokenProvider.generateToken(customUserDetails);
            List<String> listRoles = customUserDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Login successfully", new JwtResponse(jwt, customUserDetails.getUsername(), listRoles)));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject("failed", "Error: Invalid credentials", ""));
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
