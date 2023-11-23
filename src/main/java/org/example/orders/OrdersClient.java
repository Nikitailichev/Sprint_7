package org.example.orders;

import io.restassured.response.ValidatableResponse;
import org.example.Client;
import static io.restassured.RestAssured.given;

public class OrdersClient extends Client {
    private static final String PATH = "api/v1/orders";

    public ValidatableResponse create(Orders order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH)
                .then();
    }

    public ValidatableResponse cancel(int order) {

        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .put(PATH + "/cancel")
                .then();
    }

    public ValidatableResponse returnListOfOrders() {
        return given()
                .spec(getSpec())
                .when()
                .get(PATH)
                .then();
    }
}