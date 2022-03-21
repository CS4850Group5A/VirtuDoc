package com.virtudoc.web.service;

import com.virtudoc.web.entity.FileEntity;
import com.virtudoc.web.entity.UserAccount;
import com.virtudoc.web.exception.FileAccessNotPermitted;
import com.virtudoc.web.repository.FileRepository;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Date;

/**
 * Service for managing user-uploaded files (e.g. PDF, images, etc...). This is an abstract class that will
 * automatically select the best file storage method depending on what environment the web app is running in. For
 * example, when running JUnit tests outside of Docker Compose, the web app will utilize an ephemeral directory on your
 * computer. In testing and when running in side Docker Compose, FileService will utilize Minio through the S3 API. In
 * production, FileService will use Amazon S3 through a bridge SaaS provider.
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

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Creates a file from a MIME stream from a user after submitting an HTML form with a file upload function. This
     * variant of the method will upload the file with public access, meaning that anyone can access the file.
     *
     * @param file The file to be uploaded.
     * @return The file entity that was created.
     * @throws Exception Error with underlying ephemeral or block storage layer.
     */
    public FileEntity CreateFile(MultipartFile file) throws Exception {
        return createFile(file, null);
    }

    /**
     * Creates a file from a MIME stream from a user after submitting an HTML form with a file upload function. Use this
     * method instead of FileRepository.create().
     * @param file MIME stream from the HTML form submission.
     * @param request HttpServletRequest object from the controller.
     * @return Saved FileEntity entry with file location details.
     * @throws Exception Error with underlying ephemeral or block storage layer.
     */
    public FileEntity CreateFile(MultipartFile file, HttpServletRequest request) throws Exception {
        UserAccount owner = authenticationService.GetCurrentUser(request);
        return createFile(file, owner);
    }

    /**
     * Creates a file from a MIME stream from a user after submitting an HTML form with a file upload function. Use this
     * method instead of FileRepository.create().
     * @param file MIME stream from the HTML form submission.
     * @param owner UserAccount object of the user who owns the file.
     * @return Saved FileEntity entry with file location details.
     * @throws Exception Error with underlying ephemeral or block storage layer.
     */
    public FileEntity CreateFile(MultipartFile file, UserAccount owner) throws Exception {
        return createFile(file, owner);
    }

    /**
     * Internal method for creating a file in the persistent file storage layer. Do not use this method directly.
     * @param file MIME stream from the HTML form submission.
     * @param owner UserAccount object of the user who owns the file.
     * @return Saved FileEntity entry with file location details.
     * @throws Exception Error with underlying ephemeral or block storage layer.
     */
    private FileEntity createFile(MultipartFile file, UserAccount owner) throws Exception {
        String filePath = blockStorageInterface.PutFile(file);
        FileEntity newFile = new FileEntity(filePath, blockStorageInterface.GetStorageId(), new Date(), owner);
        fileRepository.save(newFile);
        if (owner != null) {
            logger.info("AUDIT: User {} uploaded file {} to {} storage", owner.getUsername(), filePath, blockStorageInterface.GetStorageId());
        } else {
            logger.info("AUDIT: User PUBLIC uploaded file {} to {} storage", filePath, blockStorageInterface.GetStorageId());
        }

        return newFile;
    }

    /**

     * Creates a file from a MIME stream from a user after submitting an HTML form with a file upload function. Use this
     * method instead of FileRepository.create().
     * @param stream File stream from another service.
     * @return Saved FileEntity entry with file location details.
     * @throws Exception Error with underlying ephemeral or block storage layer.
     */
    public FileEntity CreateFile(InputStream stream) throws Exception {
        String filePath = blockStorageInterface.PutFile(stream);
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
     * @throws Exception Error with underlying ephemeral or block storage layer.
     */
    public InputStream GetFile(FileEntity file) throws Exception {
        if (file.getOwner() == null) {
            return getFile(file);
        }
        throw new FileAccessNotPermitted(file.getFilePath(), "PUBLIC");
    }

    /**
     * Gets the contents of the specified file from the underlying ephemeral or block storage layer if the user has the
     * proper permissions. For an example of how to use the output of this function in a controller, see this answer on
     * StackOverflow: https://stackoverflow.com/a/5673356 Don't forget to close the stream when you are done!
     * @param file File contents to retrieve.
     * @param request HttpServletRequest object from the controller.
     * @return Stream that can be sent directly to client in response, or redirected to another storage system.
     * @throws Exception Error with underlying ephemeral or block storage layer.
     */
    public InputStream GetFile(FileEntity file, HttpServletRequest request) throws Exception {
        UserAccount accessUser = authenticationService.GetCurrentUser(request);
        return GetFile(file, accessUser);
    }

    /**
     * Gets the contents of the specified file from the underlying ephemeral or block storage layer if the user has the
     * proper permissions. For an example of how to use the output of this function in a controller, see this answer on
     * StackOverflow: https://stackoverflow.com/a/5673356 Don't forget to close the stream when you are done!
     * @param file File contents to retrieve.
     * @param accessUser UserAccount object of the user who is attempting to access the file.
     * @return Stream that can be sent directly to client in response, or redirected to another storage system.
     * @throws Exception Error with underlying ephemeral or block storage layer.
     */
    public InputStream GetFile(FileEntity file, UserAccount accessUser) throws Exception {
        if (file.getOwner() == null || file.getOwner().getUsername().equals(accessUser.getUsername())) { // TODO: Use complex ACL check for doctors.
            if (accessUser != null) {
                logger.info("AUDIT: User {} accessed file {} from {} storage", accessUser.getUsername(), file.getFilePath(), blockStorageInterface.GetStorageId());
            } else {
                logger.info("AUDIT: User {} accessed file {} from {} storage", "PUBLIC", file.getFilePath(), blockStorageInterface.GetStorageId());
            }
            return getFile(file);
        }
        throw new FileAccessNotPermitted(file.getFilePath(), accessUser.getUsername());
    }

    /**
     * Raw access method to get the contents of a file. Performs no checks on the user's permissions. Do not use this
     * method directly.
     * @param file File contents to retrieve.
     * @return Stream that can be sent directly to client in response, or redirected to another storage system.
     * @throws Exception Error with underlying ephemeral or block storage layer.
     */
    private InputStream getFile(FileEntity file) throws Exception {
        return blockStorageInterface.GetFile(file.getFilePath());
    }

    /**
     * Deletes a file entry, and the corresponding file data from block storage. Use this method instead of
     * FileRepository.delete().
     * @param file File to delete.
     */
    @throws Exception exception
    public void DeleteFile(FileEntity file) throws Exception {
        if (file.getOwner() != null) {
            throw new FileAccessNotPermitted(file.getFilePath(), "PUBLIC");
        }
        logger.info("AUDIT: User deleted file {} from {} storage", file.getFilePath(), blockStorageInterface.GetStorageId());
        deleteFile(file);
    }

    /**
     * Deletes a file entry, and the corresponding file data from block storage.
     * @param file File to delete.
     * @param request HttpServletRequest object from the controller.
     * @throws Exception Error with underlying ephemeral or block storage layer.
     */
    public void DeleteFile(FileEntity file, HttpServletRequest request) throws Exception {
        UserAccount accessUser = authenticationService.GetCurrentUser(request);
        DeleteFile(file, accessUser);
    }

    /**
     * Deletes a file entry, and the corresponding file data from block storage.
     * @param file File to delete.
     * @param accessUser UserAccount object of the user who is attempting to delete the file.
     * @throws Exception Error with underlying ephemeral or block storage layer.
     */
    public void DeleteFile(FileEntity file, UserAccount accessUser) throws Exception {
        if (file.getOwner() == null || file.getOwner().equals(accessUser)) {
            logger.info("AUDIT: User {} deleted file {} from {} storage", accessUser.getUsername(), file.getFilePath(), blockStorageInterface.GetStorageId());
            deleteFile(file);
        }
        throw new FileAccessNotPermitted(file.getFilePath(), accessUser.getUsername());
    }

    /**
     * Raw access method to delete a file. Performs no checks on the user's permissions. Do not use this method directly
     * except for JUnit test cleanup operations.
     * @param file File to delete.
     * @throws Exception Error with underlying ephemeral or block storage layer.
     */
    public void deleteFile(FileEntity file) throws Exception {
        blockStorageInterface.DeleteFile(file.getFilePath());
        fileRepository.delete(file);
    }

    /**
     * Counts the number of unique files stored in the storage implementation layer.
     * @return Number of files found.
     */
    public Integer FileCount() {
        return blockStorageInterface.CountObjects();
    }
}
