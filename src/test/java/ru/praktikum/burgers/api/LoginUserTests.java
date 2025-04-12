package ru.praktikum.burgers.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.burgers.api.client.UserClient;
import ru.praktikum.burgers.api.model.User;
import ru.praktikum.burgers.api.model.UserEmailPassword;
import ru.praktikum.burgers.api.util.UserGenerator;

import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTests {

    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getUser();
        userClient.createUser(user);
    }

    @After
    public void cleanUp() {
        try {
            userClient.deleteUser(accessToken);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void loginExistingUser() {
        UserEmailPassword userEmailPassword = new UserEmailPassword(user.getEmail(), user.getPassword());
        ValidatableResponse loginResponse = userClient.loginUser(userEmailPassword);
        loginResponse.assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
        accessToken = loginResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    public void loginWrongPassword() {
        UserEmailPassword userEmailPassword = new UserEmailPassword(user.getEmail(), "qwerty");
        ValidatableResponse loginResponse = userClient.loginUser(userEmailPassword);
        loginResponse.assertThat()
                .statusCode(401)
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Логин с неверным email")
    public void loginWrongEmail() {
        UserEmailPassword userEmailPassword = new UserEmailPassword("asdfsdfs", user.getPassword());
        ValidatableResponse loginResponse = userClient.loginUser(userEmailPassword);
        loginResponse.assertThat()
                .statusCode(401)
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }
}