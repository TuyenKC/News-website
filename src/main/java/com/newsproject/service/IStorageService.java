package com.newsproject.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageService {
    public ResponseEntity<?> storeFile(MultipartFile file, String usersId);
    public ResponseEntity<?> loadAll();
    public ResponseEntity<?> readFileContent(String fileName);
}
