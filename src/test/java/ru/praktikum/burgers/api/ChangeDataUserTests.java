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

public class ChangeDataUserTests {

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
    @DisplayName("Изменение авторизированного пользователя")
    public void changeDataUserWithAuth() {
        UserEmailPassword userEmailPassword = new UserEmailPassword(user.getEmail(), user.getPassword());
        ValidatableResponse loginResponse = userClient.loginUser(userEmailPassword);
        accessToken = loginResponse.extract().path("accessToken");
        ValidatableResponse updateResponse = userClient.updateUserWithAuth(UserGenerator.getUser(), accessToken);
        updateResponse.assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Изменение не авторизированного пользователя")
    public void changeDataUserWithoutAuth() {
        ValidatableResponse updateResponse = userClient.updateUserWithoutAuth(UserGenerator.getUser());
        updateResponse.assertThat()
                .statusCode(401)
                .and()
                .body("message", equalTo("You should be authorised"));
    }
}
