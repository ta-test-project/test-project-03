package com.softserve.academy.base.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public abstract class TestRunner {

    protected static final String BASE_URL = "https://www.greencity.cx.ua/#/greenCity";
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    public boolean isTestSuccessful;

    @BeforeAll
    static void setUpAll() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=eng-GB");
        if (System.getenv("GITHUB_ACTIONS") != null) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        }
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @BeforeEach
    public void setUp() {
        driver.manage().deleteAllCookies();
        driver.navigate().to(BASE_URL);
    }

    @AfterEach
    void checkTestResult(TestInfo testInfo) {
        if (!isTestSuccessful) {
            takeScreenshot(testInfo.getDisplayName());
        }
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void takeScreenshot(String testName) {
        String time = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS").format(new Date());
        try {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path dir = Paths.get("target", "screenshots");
            //Create directories if they don't exist
            Files.createDirectories(dir);
            Files.copy(screenshotFile.toPath(), dir.resolve(time + sanitizeFileName(testName) + ".png"));
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }

    private String sanitizeFileName(String name) {
        return name.replaceAll("[^a-zA-Z0-9-_]", "_");
    }
}