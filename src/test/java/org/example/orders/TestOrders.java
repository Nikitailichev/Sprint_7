package org.example.orders;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestOrders {
    private Orders defaultOrder;
    private OrdersClient ordersClient;
    private OrdersAssertions check;
    private int orderId;

    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
        check = new OrdersAssertions();
        defaultOrder = OrdersGenerator.fillOrder();
    }
    @Test
    @DisplayName("Check list Of orders can be got")
    @Description("Проверка списка заказов")
    public void checkListOfOrdersCanBeGot() {
        ValidatableResponse createResponse = ordersClient.create(defaultOrder);
        ValidatableResponse getResponse = ordersClient.returnListOfOrders();
        check.gettingSuccessfully(getResponse);
        orderId = createResponse.extract().path("track");
    }

    @After
    public void cleanUp() {
        ordersClient.cancel(orderId);
    }

}