package com.virtudoc.web;

import com.virtudoc.web.entity.FileEntity;
import com.virtudoc.web.repository.FileRepository;
import com.virtudoc.web.service.FileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class FileServiceTest {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private Environment environment;

    @AfterEach
    public void DeleteAllFiles() throws Exception {
        // Cannot use JPA rollback because there are external resources tied to
        // FileEntity objects that must be deleted first.
        for (FileEntity file: fileRepository.findAll()) {
            fileService.DeleteFile(file);
        }
        assertEquals(0, fileService.FileCount());
    }

    @Test
    public void CreatesFile() throws Exception {
        MultipartFile file = new MockMultipartFile("test.txt", "testcontent".getBytes(StandardCharsets.UTF_8));
        FileEntity fileEntry = fileService.CreateFile(file);
        // Check the file entry was saved in the database.
        assertNotEquals(0, fileEntry.getId());
        // Check that the correct storage implementation layer was used.
        if (environment.getActiveProfiles().length == 0) {
            assertEquals("ephemeral", fileEntry.getStorageType());
        } else {
            assertEquals("block", fileEntry.getStorageType());
        }
        // Check that the filesystem layer uses ambiguous naming.
        assertNotEquals("test.txt", fileEntry.getFilePath());
    }

    @Test
    public void ReadFile() throws Exception {
        MultipartFile file = new MockMultipartFile("test.txt", "testcontent".getBytes(StandardCharsets.UTF_8));
        FileEntity fileEntry = fileService.CreateFile(file);
        InputStream contentStream = fileService.GetFile(fileEntry);
        InputStreamReader isr = new InputStreamReader(contentStream, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        assertEquals("testcontent", br.readLine());
        br.close();
    }
}
