package com.softserve.academy.model;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.softserve.academy.service.BasePage;
import com.softserve.academy.service.RegistrationData;


public class RegistrationModal extends BasePage {

    private final By emailField = By.id("email");
    private final By userNameField = By.id("firstName");
    private final By passwordField = By.id("password");
    private final By confirmPasswordField = By.id("repeatPassword");
    private final By submitButton = By.cssSelector("button[type='submit'].greenStyle");
    private final By snackBar = By.cssSelector(".mdc-snackbar__label");
    private final By errorMessage = By.cssSelector(".error-message");
    private final By errorEmailField = By.id("email-err-msg");
    private final By errorNameField = By.xpath("//input[@id='firstName']/following-sibling::div");
    private final By passwordErrorField = By.className("password-not-valid");
    private final By confirmPasswordErrorField = By.id("confirm-err-msg");

    public RegistrationModal(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public RegistrationModal fillRegistrationForm(RegistrationData data) {
        type(emailField, data.getEmail());
        type(userNameField, data.getUserName());
        type(passwordField, data.getPassword());
        type(confirmPasswordField, data.getConfirmPassword());
        return this;
    }

    public void fillRegistrationForm(String email, String name, String password, String confirmPassword) {
        type(emailField, email);
        type(userNameField, name);
        type(passwordField, password);
        type(confirmPasswordField, confirmPassword + Keys.TAB);
    }

    public void fillRegistrationForm(String emptyString) {
        type(emailField, emptyString);
        type(userNameField, emptyString);
        type(passwordField, emptyString);
        type(confirmPasswordField, emptyString + Keys.TAB);
    }

    public void clickSubmitButton() {
        click(submitButton);
    }

    public boolean isSubmitButtonEnable() {
        WebElement button = driver.findElement(submitButton);
        return button.isEnabled();
    }

    public boolean isErrorMessageDisplayed() {
        return isElementPresent(errorMessage) && isVisible(errorMessage);
    }

    public boolean isSnackBarDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(snackBar));
        return isElementPresent(snackBar) && isVisible(snackBar);
    }

    public boolean isEmailErrorMessageDisplayed() {
        WebElement error = waitForVisible(errorEmailField);
        return  error.getText().toLowerCase().contains("check") ||
                error.getText().toLowerCase().contains("correctly") ||
                error.getText().toLowerCase().contains("email");
    }

    public boolean isNameErrorMessageDisplayed() {
        return waitForVisible(errorNameField).getText().toLowerCase().contains("user name");
    }
    public boolean isPasswordErrorMessageDisplayed() {
        WebElement error = waitForVisible(passwordErrorField);
        return error.getText().toLowerCase().contains("password") ||
                error.getText().toLowerCase().contains("from 8 to 20") ||
                error.getText().toLowerCase().contains("special character");
    }

    public boolean isConfirmPasswordErrorMessageDisplayed() {
        WebElement error = waitForVisible(confirmPasswordErrorField);
        return error.getText().toLowerCase().contains("required") ||
                error.getText().toLowerCase().contains("match");
    }
}
