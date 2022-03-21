package com.virtudoc.web;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class E2EWalkthroughTest {
    @LocalServerPort
    private int port;

    @Autowired
    private WebDriver driver;

    @BeforeAll
    public static void deleteScreenshots() throws IOException {
        File screenshotDirectory = new File("target/screenshots");
        if (screenshotDirectory.exists()) {
            FileUtils.cleanDirectory(screenshotDirectory);
        } else {
            screenshotDirectory.mkdirs();
        }

    }

    @Test
    public void testDriver() throws IOException {
        driver.get("http://localhost:" + port);
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "/login", "/notifications", "/appointment/show })
    public void screenshotPublicPages(String uri) throws IOException {
        driver.get("http://localhost:" + port + uri);
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        String screenshotName = uri.replace('/', '_');
        takeScreenshot(screenshotName);
    }

    private void takeScreenshot(String pageName) throws IOException {
        String screenshotPath = "target/screenshots/" + new Date().getTime() + "-" + pageName + ".png";
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File(screenshotPath));
    }
}
