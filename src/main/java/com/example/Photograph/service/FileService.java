package com.example.Photograph.service;

import com.example.Photograph.exception.FileNotFoundException;
import com.example.Photograph.exception.FileStorageException;
import com.example.Photograph.exception.InvalidContentTypeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;;

@Service
@Slf4j
public class FileService {

    @Value("${file-upload.dir}")
    private String uploadDir;
    private Path path;
    private List<String> validContentTypes;
    private Tika tika;

    @PostConstruct
    public void FileService(){
        path = Paths.get(uploadDir).toAbsolutePath().normalize();
        validContentTypes = new ArrayList<>();
        validContentTypes.add("image/png");
        validContentTypes.add("image/jpg");
        validContentTypes.add("image/jpeg");
        tika = new Tika();

        try {
            Files.createDirectories(this.path);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.");
        }
    }

    public String upload(MultipartFile file){
        //Checking the content type
        String type = "";
        try {
            type = tika.detect(file.getBytes());
        } catch (IOException e) {
            throw new InvalidContentTypeException("Invalid content type");
        }
        if(!validContentTypes.contains(type))
            throw new InvalidContentTypeException("Invalid content type");

        //Generating a unique name for the resource
        String name = UUID.randomUUID() + StringUtils.cleanPath(file.getOriginalFilename());

        //Saving the resource
        Path filePath = path.resolve(name);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return name;
        } catch (IOException e) {
            throw new FileStorageException("Cannot save the resource");
        }
    }

    public Resource getImageResource(String name){
        try{
            Path pathFile = path.resolve(name).normalize();
            Resource resource = new UrlResource(pathFile.toUri());
            if(resource.exists()){
                return resource;
            }else{
                throw new FileNotFoundException("Not found");
            }
        }catch (MalformedURLException e) {
            throw new FileNotFoundException("Not found");
        }
    }

    public String getContentType(Resource resource){
        try {
            return tika.detect(resource.getFile());
        } catch (IOException e) {
            throw new FileStorageException("Error parsing content type");
        }
    }

}
