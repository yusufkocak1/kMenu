package com.kocak.kmenuserver.service;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
@Service
public class ImageService {

        private final String IMAGE_FOLDER = "restaurant-images";

        public byte[] getImageFile(String id) throws IOException {
            String filePath = IMAGE_FOLDER + File.separator + id;
            byte[] imageBytes = Files.readAllBytes(Paths.get(filePath));
            return imageBytes;
        }


}
