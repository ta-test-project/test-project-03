package com.softserve.academy.tests;

import com.softserve.academy.base.test.TestRunner;
import com.softserve.academy.service.RegistrationData;
import com.softserve.academy.repository.UserRepository;
import com.softserve.academy.service.HomePage;
import com.softserve.academy.model.RegistrationModal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GreenCityRegistrationTests extends TestRunner {
    private RegistrationModal registration;

    @BeforeEach
    void openRegistrationModal() {
        // Arrange: Open the registration modal before each test
        registration = new HomePage(driver, wait).openRegistration();
    }

    static Stream<Arguments> provideValidRegistrationData() {
        return Stream.of(
                Arguments.of("Valid registration data", UserRepository.validUser()),
                Arguments.of("Another valid registration", UserRepository.validUser()),
                Arguments.of("Yet another valid registration", UserRepository.validUser())
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideValidRegistrationData")
    @DisplayName("Successful registration should show confirmation")
    void successfulRegistrationShowConfirm(String scenario, RegistrationData userData) {
        // Act: Fill the registration form and submit
        registration.fillRegistrationForm(userData)
                .clickSubmitButton();

        //Assert: Verify that the confirmation snackbar is displayed
        assertTrue(registration.isSnackBarDisplayed(), "Confirmation snackbar should be displayed after successful registration");
    }

    @ParameterizedTest
    @DisplayName("Invalid email format should be rejected {index}: email= ''{0}''")
    @CsvFileSource(resources = "/invalidEmail.csv")
    void shouldShowErrorForInvalidEmail(String email, String name, String password, String confirmPassword) throws InterruptedException {
        // Act: Fill the registration form and submit
        registration.fillRegistrationForm(email, "ValidUsername",
                "ValidPass123!", "ValidPass123!");

        //Assert: Verify that the email error message is displayed
        assertTrue(registration.isEmailErrorMessageDisplayed(),
                "Email error message should be displayed after invalid email registration");
        //Assert: Verify that the submit button is disabled
        assertFalse(registration.isSubmitButtonEnable(), "Submit button should be disabled");
    }

    @ParameterizedTest
    @DisplayName("All fields empty → required errors shown")
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void shouldShowErrorsForAllEmptyFields(String emptyData) throws InterruptedException {
        //Act: enter empty data
        registration.fillRegistrationForm(emptyData);
        //registration.clickSubmitButton();
        blur();

        //Assert: Verify that the errors messages are displayed after empty registration data was entered
        assertTrue(registration.isEmailErrorMessageDisplayed(),
                "Email error message should be displayed after invalid email registration");
        assertTrue(registration.isNameErrorMessageDisplayed(),
                "Name error message should be displayed after invalid name registration");
        assertTrue(registration.isPasswordErrorMessageDisplayed(),
                "Password error message should be displayed after invalid password registration");
        assertTrue(registration.isConfirmPasswordErrorMessageDisplayed(),
                "Password error message should be displayed after invalid confirm password registration");
    }

    @ParameterizedTest(name = "Invalid password {index} => scenario: ''{0}''")
    @CsvFileSource(resources = "/invalidPasswordData.csv", numLinesToSkip = 1)
    void shouldShowErrorForShortPassword(String scenario, String email, String name, String invalidPassword,
                                         String confirmPassword) throws InterruptedException {
        //Act: fill password field with invalid values
        registration.fillRegistrationForm(email, name, invalidPassword, confirmPassword);
        blur();

        //Assert: verify that password error is displayed
        assertTrue(registration.isPasswordErrorMessageDisplayed(),
                "Password error should be displayed when entered values are invalid");
    }

    @ParameterizedTest(name = "Confirm password mismatch {index}, scenario: ''{0}''")
    @CsvFileSource(resources = "/confirmPasswordData.csv", numLinesToSkip = 1)
    void shouldShowErrorForPasswordMismatch(String scenario, String email, String name,
                                            String password, String confirmPassword) throws InterruptedException {
        //Act: fill confirm password field with invalid values
        registration.fillRegistrationForm(email, name, password, confirmPassword);

        //Assert:
        assertTrue(registration.isConfirmPasswordErrorMessageDisplayed(),
                "Confirm password error should be displayed when entered values are invalid");
    }
}
