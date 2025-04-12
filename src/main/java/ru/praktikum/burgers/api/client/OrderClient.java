package ru.praktikum.burgers.api.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.burgers.api.model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {

    private static final String ORDER_PATH = "api/orders";

    @Step("Создание заказа. Пользователь авторизован")
    public ValidatableResponse createOrderWithAuth(Order order, String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then()
                .log().all();
    }

    @Step("Создание заказа. Пользователь не авторизован")
    public ValidatableResponse createOrderWithoutAuth(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then()
                .log().all();
    }

    @Step("Получение заказа. Пользователь авторизован")
    public ValidatableResponse getOrderWithAuth(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .get(ORDER_PATH)
                .then()
                .log().all();
    }

    @Step("Получение заказа. Пользователь не авторизован")
    public ValidatableResponse getOrderWithoutAuth() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then()
                .log().all();
    }
}