package com.virtudoc.web.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class FileStorageService extends IBlockStorageService {

    @Override
    public String PutFile(MultipartFile uploadedFile) throws Exception {
        String fileName = generateFileName();
        File destinationFile = new File("tmpfs/" + fileName);
        Files.copy(uploadedFile.getInputStream(), destinationFile.toPath());
        return fileName;
    }

    @Override
    public String PutFile(InputStream stream) throws Exception {
        String fileName = generateFileName();
        File destinationFile = new File("tmpfs/" + fileName);
        Files.copy(stream, destinationFile.toPath());
        return fileName;
    }

    @Override
    public InputStream GetFile(String fileName) throws Exception {
        return new FileInputStream("tmpfs/" + fileName);
    }

    @Override
    public void DeleteFile(String fileName) throws Exception {
        Path path = Paths.get("tmpfs/" + fileName);
        Files.delete(path);
    }

    @Override
    public Integer CountObjects() {
        return Objects.requireNonNull(new File("tmpfs/").list()).length;
    }

    @Override
    public String GetStorageId() {
        return "ephemeral";
    }
}
