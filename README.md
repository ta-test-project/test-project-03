# Завдання з рефакторингу: Перехід до багаторівневої архітектури

## Мета
Переробити існуючий монолітний тест `GreenCityValidRegistrationTest.java` у структурований проект, використовуючи наступні рівні:

1.  **Model**: Клас для представлення даних (наприклад, `User`), який буде містити поля: `email`, `name`, `password`.
2.  **Repository**: Логіка читання даних із зовнішніх джерел (CSV файл) та створення об'єктів моделей.
3.  **Service**: Логіка взаємодії з веб-сторінкою, винесена з тестів (наприклад, `RegistrationService`). Тут мають бути методи для відкриття модального вікна, заповнення форми та натискання кнопки.
4.  **Tests**: Самі тестові методи, які використовують сервіси для виконання дій та містять твердження (`assertions`) для перевірки результатів.
5.  **Test Runner / Base Test**: Базовий клас для налаштування драйвера (`setUp`, `tearDown`), від якого будуть успадковуватися всі тестові класи.

## Поточний стан
На даний момент весь код (локатори, налаштування драйвера, допоміжні методи та сам тест) знаходиться в одному файлі `GreenCityValidRegistrationTest.java`.

## Вимоги до виконання
- **Локатори**: Винести у сервіси або окремі константи.
- **Чистота коду**: Використовувати змістовні назви методів та класів.
- **Гнучкість**: Тест не повинен залежати від того, як саме зчитуються дані (CSV, БД тощо), він повинен працювати з об'єктами моделей.
- **WebDriver**: Налаштування браузера (включаючи англійську мову та очікування) має бути винесено в базовий клас.

---

# Refactoring Task: Transition to Multi-layered Architecture

## Goal
Refactor the existing monolithic test `GreenCityValidRegistrationTest.java` into a structured project using the following layers:

1.  **Model**: A class for data representation (e.g., `User`), containing fields: `email`, `name`, `password`.
2.  **Repository**: Logic for reading data from external sources (CSV file) and creating model objects.
3.  **Service**: Logic for interacting with the web page, extracted from tests (e.g., `RegistrationService`). This should include methods for opening the modal, filling the form, and clicking the button.
4.  **Tests**: The test methods themselves, which use services to perform actions and contain assertions to verify results.
5.  **Test Runner / Base Test**: A base class for driver setup (`setUp`, `tearDown`), from which all test classes will inherit.

## Current State
Currently, all code (locators, driver setup, helper methods, and the test itself) is located in a single file `GreenCityValidRegistrationTest.java`.

## Requirements
- **Locators**: Move to services or separate constants.
- **Code Cleanliness**: Use meaningful names for methods and classes.
- **Flexibility**: The test should not depend on how data is read (CSV, DB, etc.); it should work with model objects.
- **WebDriver**: Browser settings (including English language and waits) should be moved to the base class.
