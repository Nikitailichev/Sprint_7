package org.example.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    public static Courier random() {
        return new Courier(RandomStringUtils.randomAlphanumeric(5, 10),RandomStringUtils.randomAlphanumeric(5, 10), RandomStringUtils.randomAlphanumeric(5, 10));
    }

}