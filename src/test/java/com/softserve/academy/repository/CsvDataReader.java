package com.softserve.academy.repository;

import com.softserve.academy.model.User;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvDataReader {
    public static List<User> readUsersFromCsv(String resourcePath) {
        List<User> users = new ArrayList<>();

        // Get file from resources  folder
        try (InputStream is = CsvDataReader.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalArgumentException("File was not found by the path: " + resourcePath);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                boolean isHeader = true;

                while ((line = reader.readLine()) != null) {
                    // Miss the first line in csv file
                    if (isHeader) {
                        isHeader = false;
                        continue;
                    }

                    // Split line with comma
                    String[] data = line.split(",");
                    if (data.length >= 3) {
                        User user = new User.Builder()
                                .withEmail(data[0].trim())
                                .withName(data[1].trim())
                                .withPassword(data[2].trim())
                                .build();
                        users.add(user);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred during reading CSV: " + resourcePath, e);
        }
        return users;
    }
}