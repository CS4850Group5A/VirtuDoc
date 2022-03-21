package com.virtudoc.web;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FaviconAssetTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Tests that all images are served correctly and that security settings are properly enforced.
     * @param fileName File name to check (injected from static factory method).
     */
    @ParameterizedTest
    @MethodSource("getFaviconFiles")
    public void testFavicons(String fileName) {
        assertFalse(this.restTemplate.getForObject("http://localhost:" + port + "/" + fileName.toLowerCase(), String.class).contains("Login"), "Asset could not be found");
    }

    @ParameterizedTest
    @MethodSource("getFaviconFiles")
    public void testFaviconsHaveValidExtensions(String fileName) {
        assertTrue(fileName.contains(".png") || fileName.contains(".ico") || fileName.contains("site.webmanifest"), "Favicon file is not valid");
    }

    @ParameterizedTest
    @MethodSource("getFaviconFiles")
    public void testFaviconsHaveValidFileNames(String fileName) {
        assertTrue(fileName.contains("android") || fileName.contains("apple") || fileName.contains("favicon") || fileName.contains("site.webmanifest"), "Invalid file in static root");
    }

    private static String[] getFaviconFiles() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource("static");
        String path = url.getPath();
        File[] files = new File(path).listFiles();
        List<String> fileNamesList = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                fileNamesList.add(file.getName());
            }
        }
        return fileNamesList.toArray(String[]::new);
    }
}
