package com.softserve.academy.service;

@lombok.AllArgsConstructor
@lombok.Getter
public class RegistrationData {
    private final String email;
    private final String userName;
    private final String password;
    private final String confirmPassword;

    @Override
    public String toString() {
        return "RegistrationData{" +
                "email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}