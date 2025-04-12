package ru.praktikum.burgers.api.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.burgers.api.model.User;
import ru.praktikum.burgers.api.model.UserEmailPassword;

import static io.restassured.RestAssured.given;

public class UserClient extends RestClient {

    private static final String USER_REGISTER = "api/auth/register";
    private static final String USER_LOGIN = "api/auth/login";
    private static final String USER_DELETE = "api/auth/user";
    private static final String USER_UPDATE = "api/auth/user";

    @Step("Регистрация нового пользователя")
    public ValidatableResponse createUser(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_REGISTER)
                .then()
                .log().all();

    }

    @Step("Авторизация")
    public ValidatableResponse loginUser(UserEmailPassword credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(USER_LOGIN)
                .then()
                .log().all();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .body(accessToken)
                .when()
                .delete(USER_DELETE)
                .then()
                .log().all();
    }

    @Step("Обновление авторизованного пользователя")
    public ValidatableResponse updateUserWithAuth(User user, String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(USER_UPDATE)
                .then()
                .log().all();
    }

    @Step("Обновление не авторизованного пользователя")
    public ValidatableResponse updateUserWithoutAuth(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .patch(USER_UPDATE)
                .then()
                .log().all();
    }
}