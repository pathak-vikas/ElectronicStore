package com.vikas.ElectronicStore.services.Impl;

import ch.qos.logback.core.util.FileUtil;
import com.vikas.ElectronicStore.exceptions.BadApiRequest;
import com.vikas.ElectronicStore.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        String originalFileName = file.getOriginalFilename();
        logger.info("FileName : {}", originalFileName);
        String fileName = UUID.randomUUID().toString();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileNameWithExtension = fileName+extension;
        String fullPathWithFileName = path +fileNameWithExtension;

        logger.info("full image path: {} ", fullPathWithFileName);
        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")){
            logger.info("file extension is {} ", extension);

                //save file
            File folder = new File(path);
            if(!folder.exists()){
                folder.mkdirs();
            }
            //upload the file
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;
        } else {
                throw new BadApiRequest("File with this " + extension + "not allowed");
        }
      //  return null;
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
