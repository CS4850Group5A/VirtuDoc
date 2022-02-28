package com.virtudoc.web;

import com.virtudoc.web.service.FileStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class FileStorageServiceTest {
    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void UploadAndDeleteFile() throws Exception {
        MultipartFile file = new MockMultipartFile("test.txt", "testcontent".getBytes(StandardCharsets.UTF_8));
        String fileName = fileStorageService.PutFile(file);
        // Module should rename files and drop the extension.
        assertNotEquals("test.txt", fileName);
        fileStorageService.DeleteFile(fileName);
    }

    @Test
    public void CanRetrieveFile() throws Exception {
        MultipartFile file = new MockMultipartFile("test.txt", "testcontent".getBytes(StandardCharsets.UTF_8));
        String fileName = fileStorageService.PutFile(file);
        InputStream contentStream = fileStorageService.GetFile(fileName);
        InputStreamReader isr = new InputStreamReader(contentStream, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        assertEquals("testcontent", br.readLine());
        br.close();
        fileStorageService.DeleteFile(fileName);
    }
}
