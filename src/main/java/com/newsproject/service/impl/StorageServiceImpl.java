package com.newsproject.service.impl;

import com.newsproject.controller.ImageController;
import com.newsproject.controller.dto.ImagesDto;
import com.newsproject.controller.dto.ResponseObject;
import com.newsproject.exception.ReadFileException;
import com.newsproject.exception.StoreFileException;
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
    public ImagesDto storeFile(MultipartFile file, String usersId) throws StoreFileException {
        try{
            if(file.isEmpty()){
                throw new StoreFileException("Failed to store empty file");
            }
            if(!isImageFile(file)){
                throw new StoreFileException("You can only upload image file");
            }
            float fileSizeMegaBytes = file.getSize()/1_000_000.0f;
            if(fileSizeMegaBytes > 5.0f){
                throw new StoreFileException("File must be less than 5MB");
            }
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = new UUIDGenerator().generateUUID();
            generatedFileName = generatedFileName + "." + fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
            if(!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())){
                throw new StoreFileException("Empty store file outside current directory");
            }
            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream,destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            ImagesDto imagesDto = new ImagesDto();
            imagesDto.setTitle(generatedFileName);
            imagesDto.setExtension(fileExtension);
            imagesDto.setUserId(usersId);
            return imagesDto;
        }catch (IOException e){
            throw new StoreFileException("Cannot store file");
        }
    }

    @Override
    public List<String> loadAll() throws ReadFileException {
        try{
            Stream<Path> stream = Files.walk(this.storageFolder,1)
                    .filter(path -> !path.equals(this.storageFolder) && !path.toString().contains("._"))
                    .map(this.storageFolder::relativize);
            List<String> urls = stream.map( path -> {
                String urlPath = MvcUriComponentsBuilder.fromMethodName(ImageController.class,
                        "readFile", path.getFileName().toString()).build().toUri().toString();
                return urlPath;
            }).collect(Collectors.toList());
            return urls;
        }catch (IOException e){
            throw new ReadFileException("List file failed");
        }
    }

    @Override
    public byte[] readFileContent(String fileName) throws ReadFileException {
        try{
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }else{
               throw new ReadFileException("Can not read file");
            }

        }catch (IOException e){
                throw new ReadFileException("Can not read File");
        }
    }
}
