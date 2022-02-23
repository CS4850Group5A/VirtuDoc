package com.virtudoc.web.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * Abstract file storage layer. Handles the storage and retrieval of user-uploaded files. Operates independent
 * of the application context, and generally performs no user security or rate-limiting functions (this should be
 * handled in the FileService bean).
 *
 * @see FileService
 * @author ARMmaster17
 */
public abstract class IBlockStorageService {
    @Autowired
    protected Logger logger;

    abstract String PutFile(MultipartFile uploadedFile) throws Exception;
    abstract InputStream GetFile(String fileName) throws Exception;
    abstract void DeleteFile(String fileName) throws Exception;
    abstract Integer CountObjects();
    abstract String GetStorageId();

    protected String generateFileName() {
        return UUID.randomUUID().toString() + "-" + new Date().getTime();
    }
}
