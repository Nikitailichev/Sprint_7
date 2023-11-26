package org.example.courier;

import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.*;

public class CourierAssertions {
    public void createdSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_CREATED)
                .body("ok", is(true));
    }

    public void creationFailed(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", notNullValue());
    }

    public void creationConflicted(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_CONFLICT)
                .body("message", notNullValue());
    }

    public void authorizationSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_OK)
                .body("id", greaterThan(0));
    }

    public void authorizationWithoutRequiredField(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", notNullValue());
    }

    public void authorizationWithInvalidField(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", notNullValue());
    }
}