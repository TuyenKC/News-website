package com.newsproject.service.impl;

import com.newsproject.controller.ImageController;
import com.newsproject.controller.dto.ImagesDto;
import com.newsproject.controller.dto.ResponseObject;
import com.newsproject.repository.entity.Images;
import com.newsproject.service.IStorageService;
import com.newsproject.service.ImagesService;
import com.newsproject.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
public class StorageServiceImpl implements IStorageService {

    private final Path storageFolder = Paths.get("uploads");
    public StorageServiceImpl(){
        try{
            Files.createDirectories(storageFolder);
        }catch (Exception e){
            throw new RuntimeException("Cannot initialize storage", e);
        }
    }
    public boolean isImageFile(MultipartFile file){
        String fileExtensions = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"png","jpg","jpeg","bmp"}).contains(fileExtensions.trim().toLowerCase());
    }
    @Override
    public ResponseEntity<?> storeFile(MultipartFile file, String usersId) {
        try{
            if(file.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseObject("failed", "Failed to store empty file", "")
                );
            }
            if(!isImageFile(file)){
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseObject("failed", "You can only upload image file", "")
                );
            }
            float fileSizeMegaBytes = file.getSize()/1_000_000.0f;
            if(fileSizeMegaBytes > 5.0f){
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseObject("failed", "File must be less than 5MB", "")
                );
            }
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = new UUIDGenerator().generateUUID();
            generatedFileName = generatedFileName + "." + fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
            if(!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())){
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseObject("failed", "Empty store file outside current directory", "")
                );
            }
            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream,destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            ImagesDto imagesDto = new ImagesDto();
            imagesDto.setTitle(generatedFileName);
            imagesDto.setExtension(fileExtension);
            imagesDto.setUserId(usersId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "upload file successfully", imagesDto)
            );
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Cannot store file", "")
            );
        }
    }

    @Override
    public ResponseEntity<?> loadAll() {
        try{
            Stream<Path> stream = Files.walk(this.storageFolder,1)
                    .filter(path -> !path.equals(this.storageFolder) && !path.toString().contains("._"))
                    .map(this.storageFolder::relativize);
            List<String> urls = stream.map( path -> {
                String urlPath = MvcUriComponentsBuilder.fromMethodName(ImageController.class,
                        "readFile", path.getFileName().toString()).build().toUri().toString();
                return urlPath;
            }).collect(Collectors.toList());
            return ResponseEntity.ok(new ResponseObject("ok","List file successfully",urls));
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "List file failed", "")
            );        }
    }

    @Override
    public ResponseEntity<?> readFileContent(String fileName) {
        try{
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(bytes);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseObject("failed", "Could not read file" + fileName, "")
                );
            }

        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Could not read file" + fileName, "")
            );
        }
    }
}
