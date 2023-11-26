package org.example.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.Client;
import static io.restassured.RestAssured.given;


public class CourierClient extends Client {

    private static final String PATH = "api/v1/courier";

    @Step("Creating a new courier")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then();
    }

    @Step("Logging in a courier")
    public ValidatableResponse login(CourierLogin credentials) {
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(PATH + "/login")
                .then();
    }

    @Step("Deleting a courier with ID: {id}")
    public ValidatableResponse delete(int courierId) {
        String json = String.format("{\"id\": \"%d\"}", courierId);
        return given()
                .spec(getSpec())
                .body(json)
                .when()
                .delete(PATH + "/" + courierId)
                .then();
    }
}