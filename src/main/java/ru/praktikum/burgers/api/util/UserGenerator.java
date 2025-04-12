package ru.praktikum.burgers.api.util;

import org.apache.commons.lang3.RandomStringUtils;
import ru.praktikum.burgers.api.model.User;

public class UserGenerator {

    public static User getUser() {
        String name = RandomStringUtils.randomAlphabetic(8);
        String email = RandomStringUtils.randomAlphabetic(8) + "@mails.ru";
        String password = RandomStringUtils.randomAlphabetic(8);
        return new User(name, email, password);
    }
}