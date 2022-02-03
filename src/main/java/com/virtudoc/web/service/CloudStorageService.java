package com.virtudoc.web.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.virtudoc.web.entity.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * Implementation of the abstract storage layer that communicates with an S3-compatible datastore.
 *
 * @see IBlockStorageService
 * @author ARMmaster17
 */
@Service
@Profile(value={"prod", "test", "dev-managed"})
public class CloudStorageService extends IBlockStorageService {
    @Autowired
    private AmazonS3Client amazonS3Client;

    @Value("${file.bucket}")
    private String cloudFileBucket;

    @Value("${file.endpoint}")
    private String cloudEndpoint;

    /**
     * Uploads a file to an S3-compatible datastore, and creates a corresponding
     * FileEntity entry in the database.
     * @param uploadedFile MIME stream from client that contains file to be uploaded.
     * @return Corresponding FileEntity entry in the database.
     * @throws Exception Error saving the temporary file or uploading to the cloud provider.
     */
    @Override
    public FileEntity PutFile(MultipartFile uploadedFile) throws Exception {
        String fileName = generateFileName();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(uploadedFile.getSize());
        PutObjectRequest request = new PutObjectRequest(cloudFileBucket, fileName, uploadedFile.getInputStream(), metadata);
        try {
            amazonS3Client.putObject(request);
        } catch (Exception e) {
            logger.error("error occurred when communicating with S3", e);
            // Throwing a blanket exception to prevent internal infrastructure URIs from leaking in a fatal crash.
            throw new Exception("error communicating with cloud block storage provider");
        }
        // TODO: Move this function up to the FileService class.
        return generateFileEntry(fileName);
    }

    /**
     * Gets a MIME stream of the decrypted contents of a file from the underlying S3-backed datastore.
     * @param file Saved file to retrieve.
     * @return MIME stream to read file contents from.
     * @throws Exception Error communicating with S3-backed datastore.
     */
    @Override
    public InputStream GetFile(FileEntity file) throws Exception {
        S3Object object = amazonS3Client.getObject(cloudFileBucket, file.getFilePath());
        return object.getObjectContent().getDelegateStream();
    }

    /**
     * Deletes a file from the S3-backed datastore.
     * @param file File to delete.
     * @throws Exception IO error communicating with the S3-backed datastore.
     */
    @Override
    public void DeleteFile(FileEntity file) throws Exception {
        amazonS3Client.deleteObject(cloudFileBucket, file.getFilePath());
    }

    /**
     * Specifies the underlying storage type for debugging purposes.
     * @return Storage type.
     */
    @Override
    protected String getStorageId() {
        return "block";
    }
}

