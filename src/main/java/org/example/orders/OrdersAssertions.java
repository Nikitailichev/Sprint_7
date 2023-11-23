package org.example.orders;

import io.restassured.response.ValidatableResponse;


import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersAssertions {


    public void createdSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_CREATED)
                .body("track", notNullValue());
    }

    public void gettingSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue());
    }
}