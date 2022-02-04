package com.virtudoc.web.service;

import com.virtudoc.web.entity.FileEntity;
import com.virtudoc.web.repository.FileRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;

/**
 * Service for managing user-uploaded files (e.g. PDF, images, etc...). This is an abstract class that will
 * automatically select the best file storage method depending on what environment the web app is running in. For
 * example, when running JUnit tests outside of Docker Compose, the web app will utilize an ephemeral directory on your
 * computer. In testing and when running in side Docker Compose, FileService will utilize Minio through the S3 API. In
 * production, FileService will use Amazon S3 through a bridge SaaS provider. Note that this service does not perform
 * any kind of user authentication.
 *
 * @author ARMmaster17
 */
@Service
public class FileService {
    @Autowired
    private IBlockStorageService blockStorageInterface;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private Logger logger;

    /**
     * Creates a file from a MIME stream from a user after submitting an HTML form with a file upload function. Use this
     * method instead of FileRepository.create().
     * @param file MIME stream from the HTML form submission.
     * @return Saved FileEntity entry with file location details.
     * @throws Exception Error with underlying ephemeral or block storage layer.
     */
    public FileEntity CreateFile(MultipartFile file) throws Exception {
        String filePath = blockStorageInterface.PutFile(file);
        FileEntity newFile = new FileEntity(filePath, blockStorageInterface.GetStorageId(), new Date());
        fileRepository.save(newFile);
        // TODO: This should have the actual user ID once authentication/ACL/RBA is fully implemented.
        logger.info("AUDIT: User uploaded file {} to {} storage", filePath, blockStorageInterface.GetStorageId());
        return newFile;
    }

    /**
     * Gets the contents of the specified file from the underlying ephemeral or block storage layer. For an example of
     * how to use the output of this function in a controller, see this answer on StackOverflow:
     * https://stackoverflow.com/a/5673356 Don't forget to close the stream when you are done!
     * @param file File contents to retrieve.
     * @return Stream that can be sent directly to client in response, or redirected to another storage system.
     */
    public InputStream GetFile(FileEntity file) throws Exception {
        logger.info("AUDIT: User accessed file {} from {} storage", file.getFilePath(), blockStorageInterface.GetStorageId());
        return blockStorageInterface.GetFile(file.getFilePath());
    }

    /**
     * Deletes a file entry, and the corresponding file data from block storage. Use this method instead of
     * FileRepository.delete().
     * @param file File to delete.
     */
    public void DeleteFile(FileEntity file) throws Exception {
        blockStorageInterface.DeleteFile(file.getFilePath());
        fileRepository.delete(file);
        logger.info("AUDIT: User deleted file {} from {} storage", file.getFilePath(), blockStorageInterface.GetStorageId());
    }

    /**
     * Counts the number of unique files stored in the storage implementation layer.
     * @return Number of files found.
     */
    public Integer FileCount() {
        return blockStorageInterface.CountObjects();
    }
}
