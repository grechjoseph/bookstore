package com.jg.bookstore.domain.enums;

import lombok.Getter;

import java.util.List;

@Getter
public enum OrderStatus {

    CREATED, CONFIRMED, PAID,
    CANCELLED, REFUNDED, SHIPPED;

    private List<OrderStatus> nextSteps;

    static {
        CREATED.nextSteps = List.of(CONFIRMED, CANCELLED);
        CONFIRMED.nextSteps = List.of(PAID, CANCELLED);
        PAID.nextSteps = List.of(REFUNDED, SHIPPED);
    }

    public boolean isUpdatable() {
        return !List.of(PAID, CANCELLED, REFUNDED, SHIPPED).contains(this);
    }

    public boolean isFinal() {
        return List.of(CANCELLED, REFUNDED, SHIPPED).contains(this);
    }

}
