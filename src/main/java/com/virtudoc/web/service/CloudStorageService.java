package com.virtudoc.web.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
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
@Primary
public class CloudStorageService extends IBlockStorageService {
    @Autowired
    private AmazonS3 amazonS3Client;

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
    public String PutFile(MultipartFile uploadedFile) throws Exception {
        String fileName = generateFileName();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(uploadedFile.getSize());
        PutObjectRequest request = new PutObjectRequest(cloudFileBucket, fileName, uploadedFile.getInputStream(), metadata);
        try {
            if (!amazonS3Client.doesBucketExistV2(cloudFileBucket)) {
                amazonS3Client.createBucket(cloudFileBucket);
            }
            amazonS3Client.putObject(request);
        } catch (Exception e) {
            logger.error("error occurred when communicating with S3", e);
            // Throwing a blanket exception to prevent internal infrastructure URIs from leaking in a fatal crash.
            throw new Exception("error communicating with cloud block storage provider");
        }
        return fileName;
    }

    /**
     * Uploads a file to an S3-compatible datastore, and creates a corresponding
     * FileEntity entry in the database.
     * @param uploadedFile MIME stream from client that contains file to be uploaded.
     * @return Corresponding FileEntity entry in the database.
     * @throws Exception Error saving the temporary file or uploading to the cloud provider.
     */
    @Override
    public String PutFile(InputStream stream) throws Exception {
        String fileName = generateFileName();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(stream.available());
        PutObjectRequest request = new PutObjectRequest(cloudFileBucket, fileName, stream, metadata);
        try {
            if (!amazonS3Client.doesBucketExistV2(cloudFileBucket)) {
                amazonS3Client.createBucket(cloudFileBucket);
            }
            amazonS3Client.putObject(request);
        } catch (Exception e) {
            logger.error("error occurred when communicating with S3", e);
            // Throwing a blanket exception to prevent internal infrastructure URIs from leaking in a fatal crash.
            throw new Exception("error communicating with cloud block storage provider");
        }
        return fileName;
    }

    /**
     * Gets a MIME stream of the decrypted contents of a file from the underlying S3-backed datastore.
     * @param fileName Saved file to retrieve.
     * @return MIME stream to read file contents from.
     * @throws Exception Error communicating with S3-backed datastore.
     */
    @Override
    public InputStream GetFile(String fileName) throws Exception {
        S3Object object = amazonS3Client.getObject(cloudFileBucket, fileName);
        return object.getObjectContent().getDelegateStream();
    }

    /**
     * Deletes a file from the S3-backed datastore.
     * @param fileName File to delete.
     * @throws Exception IO error communicating with the S3-backed datastore.
     */
    @Override
    public void DeleteFile(String fileName) throws Exception {
        amazonS3Client.deleteObject(cloudFileBucket, fileName);
    }

    /**
     * Used for testing and auditing purposes. Counts the number of unique
     * objects in the cloud storage layer.
     * @return
     */
    @Override
    public Integer CountObjects() {
        return amazonS3Client.listObjects(cloudFileBucket).getMaxKeys();
    }

    /**
     * Specifies the underlying storage type for debugging purposes.
     * @return Storage type.
     */
    @Override
    public String GetStorageId() {
        return "block";
    }
}

