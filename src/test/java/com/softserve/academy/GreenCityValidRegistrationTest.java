package com.softserve.academy;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GreenCityValidRegistrationTest {

    // Locators
    private static final By SIGN_UP_BUTTON = By.cssSelector(".header_sign-up-btn > span");
    private static final By EMAIL_FIELD = By.id("email");
    private static final By FIRST_NAME_FIELD = By.id("firstName");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By REPEAT_PASSWORD_FIELD = By.id("repeatPassword");
    private static final By SUBMIT_BUTTON = By.cssSelector(".greenStyle");
    private static final By SUCCESS_MESSAGE = By.cssSelector(".mdc-snackbar__label");

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    static void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=en");
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        
        driver = WebDriverManager.chromedriver().capabilities(options).create();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @BeforeEach
    void openUrl() {
        driver.navigate().to("https://www.greencity.cx.ua/#/greenCity");
    }

    @ParameterizedTest(name = "Data from file - Email: {0}")
    @DisplayName("Check registration via CsvFileSource (external file)")
    @CsvFileSource(resources = "/registration_data.csv", numLinesToSkip = 1)
    void testWithCsvFileSource(String email, String name, String password) {
        String uniqueEmail = generateUniqueEmail(email);
        
        openRegistrationModal();
        fillRegistrationForm(uniqueEmail, name, password);
        submitRegistration();
        verifySuccessRegistration();
    }

    // Helper methods
    private String generateUniqueEmail(String email) {
        return "test" + UUID.randomUUID().toString().substring(0, 8) + email;
    }

    private void openRegistrationModal() {
        wait.until(ExpectedConditions.elementToBeClickable(SIGN_UP_BUTTON)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_FIELD));
    }

    private void fillRegistrationForm(String email, String name, String password) {
        enterText(EMAIL_FIELD, email);
        enterText(FIRST_NAME_FIELD, name);
        enterText(PASSWORD_FIELD, password);
        enterText(REPEAT_PASSWORD_FIELD, password);
    }

    private void enterText(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    private void submitRegistration() {
        WebElement btnSubmit = wait.until(ExpectedConditions.elementToBeClickable(SUBMIT_BUTTON));
        if (btnSubmit.isEnabled()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnSubmit);
        }
    }

    private void verifySuccessRegistration() {
        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_MESSAGE));
        String msg = successMsg.getText();
        String expectedSnippet = "successfully registered";
        assertTrue(msg.contains(expectedSnippet), "Success message should contain: '" + expectedSnippet + "'");
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
