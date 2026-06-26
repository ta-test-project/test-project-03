package com.softserve.academy.service;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class RegistrationService{
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By SIGN_UP_BUTTON = By.cssSelector(".header_sign-up-btn > span");
    private final By EMAIL_FIELD = By.id("email");
    private final By USER_NAME_FIELD = By.id("firstName");
    private final By PASSWORD_FIELD = By.id("password");
    private final By CONFIRM_PASSWORD_FIELD = By.id("repeatPassword");
    private final By SUBMIT_BUTTON = By.cssSelector(".greenStyle");
    private final By SNACKBAR = By.cssSelector(".mdc-snackbar__label");
    private final By ERROR_MESSAGE = By.cssSelector(".error-message");

    public RegistrationService(WebDriver driver, WebDriverWait wait) {

        this.driver = driver;
        this.wait = wait;
    }

    public void openRegistrationModal() {
        wait.until(ExpectedConditions.elementToBeClickable(SIGN_UP_BUTTON)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_FIELD));
    }

    public void fillRegistrationForm(String email, String name, String password) {
        // We should create unique email, because after the first successful registration
        // next test will fail
        type(EMAIL_FIELD, uniqueEmail());
        type(USER_NAME_FIELD, name);
        type(PASSWORD_FIELD, password);
        type(CONFIRM_PASSWORD_FIELD, password + Keys.TAB);
    }

    private static String uniqueEmail() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder email = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            email.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return email.append("@gmail.com").toString();
    }

    public void clickSubmitButton() {
        click(SUBMIT_BUTTON);
    }

    public boolean isSubmitButtonEnable() {
        WebElement button = driver.findElement(SUBMIT_BUTTON);
        return button.isEnabled();
    }

    public boolean isErrorMessageDisplayed() {
        return isElementPresent(ERROR_MESSAGE) && isVisible(ERROR_MESSAGE);
    }

    public boolean isSnackBarDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(SNACKBAR));
        return isElementPresent(SNACKBAR) && isVisible(SNACKBAR);
    }

    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    protected boolean isVisible(By locator) {

        return waitForVisible(locator).isDisplayed();
    }
}