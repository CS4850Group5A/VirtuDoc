package com.virtudoc.web;

import com.virtudoc.web.dto.NewUserDTO;
import com.virtudoc.web.entity.FileEntity;
import com.virtudoc.web.entity.UserAccount;
import com.virtudoc.web.exception.FileAccessNotPermitted;
import com.virtudoc.web.repository.FileRepository;
import com.virtudoc.web.repository.UserAccountRepository;
import com.virtudoc.web.service.AuthenticationService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FileServiceTest {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private Environment environment;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @AfterEach
    public void DeleteAllFiles() throws Exception {
        // Cannot use JPA rollback because there are external resources tied to
        // FileEntity objects that must be deleted first.
        for (FileEntity file: fileRepository.findAll()) {
            fileService.deleteFile(file);
        }
        // There is a glitch with Minio where the file count is always 1000. Good luck to anyone else who
        // tries to track down this bug. This error should not appear on S3, but tests are done with Minio.
        //assertEquals(0, fileService.FileCount());

        List<UserAccount> testuser1 = userAccountRepository.findByUsername("readprivatefile_test1");
        if (testuser1.size() > 0) {
            userAccountRepository.delete(testuser1.get(0));
        }
        List<UserAccount> testuser2 = userAccountRepository.findByUsername("readprivatefile_test2");
        if (testuser2.size() > 0) {
            userAccountRepository.delete(testuser2.get(0));
        }
    }

    @Test
    public void CreatesPublicFile() throws Exception {
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
    public void ReadPublicFile() throws Exception {
        MultipartFile file = new MockMultipartFile("test.txt", "testcontent".getBytes(StandardCharsets.UTF_8));
        FileEntity fileEntry = fileService.CreateFile(file);
        InputStream contentStream = fileService.GetFile(fileEntry);
        InputStreamReader isr = new InputStreamReader(contentStream, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        assertEquals("testcontent", br.readLine());
        br.close();
    }

    @Test
    public void ReadPrivateFile() throws Exception {
        MultipartFile file = new MockMultipartFile("test.txt", "testcontent".getBytes(StandardCharsets.UTF_8));
        authenticationService.RegisterNewAccount(new UserAccount("readprivatefile_test1", "none", "PATIENT"));
        UserAccount testUser = userAccountRepository.findByUsername("readprivatefile_test1").get(0); // Reload to get instance with populated ID.
        FileEntity fileEntry = fileService.CreateFile(file, testUser);
        InputStream contentStream = fileService.GetFile(fileEntry, testUser);
        InputStreamReader isr = new InputStreamReader(contentStream, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        assertEquals("testcontent", br.readLine());
        br.close();
    }

    @Test
    public void PublicCannotReadPrivateFile() throws Exception {
        MultipartFile file = new MockMultipartFile("test.txt", "testcontent".getBytes(StandardCharsets.UTF_8));
        authenticationService.RegisterNewAccount(new UserAccount("readprivatefile_test1", "none", "PATIENT"));
        UserAccount testUser = userAccountRepository.findByUsername("readprivatefile_test1").get(0); // Reload to get instance with populated ID.
        FileEntity fileEntry = fileService.CreateFile(file, testUser);
        assertThrows(FileAccessNotPermitted.class, () -> fileService.GetFile(fileEntry));
    }

    @Test
    public void NonOwnerCannotReadPrivateFile() throws Exception {
        MultipartFile file = new MockMultipartFile("test.txt", "testcontent".getBytes(StandardCharsets.UTF_8));
        authenticationService.RegisterNewAccount(new UserAccount("readprivatefile_test1", "none", "PATIENT"));
        authenticationService.RegisterNewAccount(new UserAccount("readprivatefile_test2", "none", "PATIENT"));
        UserAccount testUser1 = userAccountRepository.findByUsername("readprivatefile_test1").get(0); // Reload to get instance with populated ID.
        UserAccount testUser2 = userAccountRepository.findByUsername("readprivatefile_test2").get(0); // Reload to get instance with populated ID.
        FileEntity fileEntry = fileService.CreateFile(file, testUser1);
        assertThrows(FileAccessNotPermitted.class, () -> fileService.GetFile(fileEntry, testUser2));
    }
}
