package com.softserve.academy.repository;

import com.softserve.academy.service.RegistrationData;

import java.util.UUID;

public class UserRepository {
    private UserRepository() {
    }

    public static RegistrationData validUser() {
        return new RegistrationData(uniqueEmail(), "TestUser", "Password123!", "Password123!");
    }

    public static RegistrationData invalidUserWithPassword(String password) {
        return new RegistrationData(uniqueEmail(), "TestUser", password, password);
    }

    private static String uniqueEmail() {
        return UUID.randomUUID().toString().substring(0, 8) + "@gmail.com";
    }
}
