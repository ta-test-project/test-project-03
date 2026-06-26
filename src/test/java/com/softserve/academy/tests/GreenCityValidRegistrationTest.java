package com.softserve.academy.tests;

import com.softserve.academy.base.test.TestRunner;
import com.softserve.academy.model.User;
import com.softserve.academy.repository.CsvDataReader;
import com.softserve.academy.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GreenCityRegistrationTests extends TestRunner {
    private RegistrationService registration;

    @BeforeEach
    void openSite() {
        driver.get("https://www.greencity.cx.ua/#/greenCity");
        registration = new RegistrationService(driver, wait);
    }


    static List<User> userProvider() {
        return CsvDataReader.readUsersFromCsv("/registration_data.csv");
    }

    @ParameterizedTest(name = "Data from model - User: {0}")
    @DisplayName("Check registration via CSV model reader")
    @MethodSource("userProvider")
    void testWithCsvFileSource(User user) {
        registration.openRegistrationModal();
        registration.fillRegistrationForm(user.getEmail(), user.getName(), user.getPassword());

        assertTrue(registration.isSubmitButtonEnable(),
                "Submit button should be enabled");
        assertFalse(registration.isErrorMessageDisplayed(),
                "Error message should not be displayed)");

        registration.clickSubmitButton();

        assertTrue(registration.isSnackBarDisplayed(),
                "Confirmation snackbar should be displayed after successful registration");

    }
}