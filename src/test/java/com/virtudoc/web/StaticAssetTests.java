package com.virtudoc.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests that static assets are served correctly.
 *
 * @author ARMmaster17
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StaticAssetTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Tests that all images are served correctly and that security settings are properly enforced.
     * @param fileName File name to check (injected from static factory method).
     */
    @ParameterizedTest
    @MethodSource("getImageFiles")
    public void testImages(String fileName) {
        assert(this.restTemplate.getForObject("http://localhost:" + port + "/img/" + fileName.toLowerCase(), String.class).contains("PNG"));
    }

    @ParameterizedTest
    @MethodSource("getImageFiles")
    public void testImagesHaveValidExtensions(String fileName) {
        assertTrue(fileName.contains(".png"), "Image file must be a .png");
    }

    @ParameterizedTest
    @MethodSource("getImageFiles")
    public void testImagesHaveValidFileNames(String fileName) {
        assertFalse(fileName.contains(" "), "File name must not contain spaces.");
    }

    private static String[] getImageFiles() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource("static/img");
        String path = url.getPath();
        return new java.io.File(path).list();
    }
}
