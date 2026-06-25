package com.softserve.academy.service;


import com.softserve.academy.service.BasePage;
import com.softserve.academy.model.RegistrationModal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

    private final By signUpButton = By.cssSelector(".header_sign-up-btn > span");

    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public RegistrationModal openRegistration() {
        click(signUpButton);
        return new RegistrationModal(driver, wait);
    }
}