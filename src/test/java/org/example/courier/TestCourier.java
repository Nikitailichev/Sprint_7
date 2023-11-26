package org.example.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCourier {

    private Courier randomCourier;
    private CourierClient courierClient;
    private CourierAssertions check;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        check = new CourierAssertions();
        randomCourier = CourierGenerator.random();
    }

    // Тесты "создание курьера"
    @Test
    @DisplayName("Check Create Courier")
    @Description("Проверка успешного создания  курьера")

    public void checkCreateCourier() {
        ValidatableResponse createResponse = courierClient.create(randomCourier);
        check.createdSuccessfully(createResponse);
        courierId = courierClient.login(CourierLogin.from(randomCourier)).extract().path("id");
    }
    @Test
    @DisplayName("Check impossible create same courier")
    @Description("Проверка создания курьера с теми же данными")
    public void checkImpossibleCreateSameCourier() {
        courierClient.create(randomCourier);
        ValidatableResponse createResponse = courierClient.create(randomCourier);
        courierId = courierClient.login(CourierLogin.from(randomCourier)).extract().path("id");
        check.creationConflicted(createResponse);

    }
    @Test
    @DisplayName("Сheck impossible create courier without login")
    @Description("Проверка создания курьера без логина")
    public void checkImpossibleCreateCourierWithoutLogin() {
        randomCourier.setLogin(null);
        ValidatableResponse createResponse = courierClient.create(randomCourier);
        check.creationFailed(createResponse);
    }
    @Test
    @DisplayName("Check impossible create courier without Password")
    @Description("Проверка создания курьера без пароля")
    public void checkImpossibleCreateCourierWithoutPassword() {
        randomCourier.setPassword(null);
        ValidatableResponse createResponse = courierClient.create(randomCourier);
        check.creationFailed(createResponse);
    }
    @Test
    @DisplayName("Check impossible create courier without last name")
    @Description("Проверка создания курьера без фамилии")
    public void checkImpossibleCreateCourierWithoutLastName() {
        randomCourier.setLastName(null);
        ValidatableResponse createResponse = courierClient.create(randomCourier);
        courierId = courierClient.login(CourierLogin.from(randomCourier)).extract().path("id");
        check.creationFailed(createResponse);
    }
    @Test
    @DisplayName("Check impossible create courier with busy login")
    @Description("Проверка создания курьера с занятым логином")
    public void checkImpossibleCreateCourierWithBusyLogin() {
        randomCourier.setLogin("SameLogin");
        courierClient.create(randomCourier);
        Courier secondCourier = CourierGenerator.random();
        secondCourier.setLogin("SameLogin");
        ValidatableResponse createResponse = courierClient.create(secondCourier);
        courierId = courierClient.login(CourierLogin.from(randomCourier)).extract().path("id");
        check.creationConflicted(createResponse);

    }

    //Тесты  "логин курьера"
    @Test
    @DisplayName("Check courier login")
    @Description("Проверка успешной авторизации курьера")
    public void checkCourierLogin() {
        courierClient.create(randomCourier);
        CourierLogin credentials = CourierLogin.from(randomCourier);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.authorizationSuccess(loginResponse);
        courierId = loginResponse.extract().path("id");
    }
    @Test
    @DisplayName("Check authorization without login")
    @Description("Проверка авторизации без логина")
    public void checkAuthorizationWithoutLogin() {
        courierClient.create(randomCourier);
        courierId = courierClient.login(CourierLogin.from(randomCourier)).extract().path("id");
        CourierLogin credentials = CourierLogin.from(randomCourier);
        credentials.setLogin(null);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.authorizationWithoutRequiredField(loginResponse);
    }
    @Test
    @DisplayName("Check authorization without password")
    @Description("Проверка авторизации без пароля")
    public void checkAuthorizationWithoutPassword() {
        courierClient.create(randomCourier);
        courierId = courierClient.login(CourierLogin.from(randomCourier)).extract().path("id");
        CourierLogin credentials = CourierLogin.from(randomCourier);
        credentials.setPassword(null);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.authorizationWithoutRequiredField(loginResponse);
    }
    @Test
    @DisplayName("Check authorization with invalid login")
    @Description("Проверка авторизации курьера с некорректным логиным")
    public void checkAuthorizationWithInvalidLogin() {
        courierClient.create(randomCourier);
        courierId = courierClient.login(CourierLogin.from(randomCourier)).extract().path("id");
        CourierLogin credentials = CourierLogin.from(randomCourier);
        credentials.setLogin("InvalidLogin");
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.authorizationWithInvalidField(loginResponse);
    }
    @Test
    @DisplayName("Check authorization with invalid password")
    @Description("Проверка авторизации курьера с некорректным паспортом")
    public void checkAuthorizationWithInvalidPassword() {
        courierClient.create(randomCourier);
        courierId = courierClient.login(CourierLogin.from(randomCourier)).extract().path("id");
        CourierLogin credentials = CourierLogin.from(randomCourier);
        credentials.setPassword("InvalidPassword");
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.authorizationWithInvalidField(loginResponse);
    }
    @Test
    @DisplayName("check authorization non-existent courier")
    @Description("Проверка авторизации несуществуещего курьера")
    public void checkAuthorizationNonexistentCourier() {
        CourierLogin credentials = CourierLogin.from(randomCourier);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.authorizationWithInvalidField(loginResponse);
    }

    @After
    public void cleanUp() {
        if (courierId > 0) {
            courierClient.delete(courierId);
        }
    }
}