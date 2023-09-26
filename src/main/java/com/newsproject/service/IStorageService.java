package com.newsproject.service;

import com.newsproject.controller.dto.ImagesDto;
import com.newsproject.exception.ReadFileException;
import com.newsproject.exception.StoreFileException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface IStorageService {
    public ImagesDto storeFile(MultipartFile file, String usersId) throws StoreFileException;
    public List<String> loadAll() throws ReadFileException;
    public byte[] readFileContent(String fileName) throws ReadFileException;
}
