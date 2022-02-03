package com.virtudoc.web.service;

import com.virtudoc.web.entity.FileEntity;
import com.virtudoc.web.repository.FileRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

public abstract class IBlockStorageService {
    @Autowired
    protected FileRepository fileRepository;

    @Autowired
    protected Logger logger;

    abstract FileEntity PutFile(MultipartFile uploadedFile) throws Exception;
    abstract InputStream GetFile(FileEntity file) throws Exception;
    abstract void DeleteFile(FileEntity file) throws Exception;
    abstract String getStorageId();

    protected String generateFileName() {
        return UUID.randomUUID().toString() + "-" + new Date().getTime();
    }

    protected FileEntity generateFileEntry(String fileName) {
        FileEntity fileEntry = new FileEntity(fileName, getStorageId(), new Date());
        fileEntry = fileRepository.save(fileEntry);
        return fileEntry;
    }
}
