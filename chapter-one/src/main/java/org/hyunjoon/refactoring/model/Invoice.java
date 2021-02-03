package org.hyunjoon.refactoring.model;

import java.util.List;
import java.util.Objects;

public class Invoice {
    private final String customer;
    private final List<Performance> performances;

    public Invoice(String customer, List<Performance> performances) {
        this.customer = Objects.requireNonNull(customer, "customer");
        this.performances = Objects.requireNonNull(performances, "performances");
    }

    public String getCustomer() {
        return customer;
    }

    public List<Performance> getPerformances() {
        return performances;
    }
}
