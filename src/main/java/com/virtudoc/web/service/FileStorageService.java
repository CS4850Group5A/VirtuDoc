package com.virtudoc.web.service;

import com.virtudoc.web.entity.FileEntity;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Profile(value = "")
public class FileStorageService extends IBlockStorageService {

    @Override
    public FileEntity PutFile(MultipartFile uploadedFile) throws Exception {
        String fileName = generateFileName();
        File destinationFile = new File("tmpfs/" + fileName);
        Files.copy(uploadedFile.getInputStream(), destinationFile.toPath());
        return generateFileEntry(fileName);
    }

    @Override
    public InputStream GetFile(FileEntity file) throws Exception {
        return new FileInputStream("tmpfs/" + file.getFilePath());
    }

    @Override
    public void DeleteFile(FileEntity file) throws Exception {
        Path path = Paths.get("tmpfs/" + file.getFilePath());
        Files.delete(path);
        fileRepository.delete(file);
    }

    @Override
    protected String getStorageId() {
        return "ephemeral";
    }
}
