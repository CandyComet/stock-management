package com.resrfullapi.api.testing.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;


@Service
public class FileUploadServices {

    String IMAGE_FOLDER_PATH = "file_upload/images/";

    public String saveImage(MultipartFile file) {
        try {
            Path directoryPath = Paths.get(IMAGE_FOLDER_PATH);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            String[] splitName = file.getOriginalFilename().split("\\.");
            String uniqueName = System.currentTimeMillis() + "." + splitName[splitName.length - 1];


            Path imagePath = directoryPath.resolve(uniqueName);
            Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);



            return uniqueName;

        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
