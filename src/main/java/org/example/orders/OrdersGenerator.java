package org.example.orders;

public class OrdersGenerator {
    public static Orders fillOrder() {
        return new Orders("Арсен",
                "Венгер",
                "Эмирейтс",
                "Хайбери",
                "+79999999999",
                5,
                "2023-04-06",
                "qwerty");
    }
}