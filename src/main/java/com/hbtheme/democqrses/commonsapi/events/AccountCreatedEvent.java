package com.hbtheme.democqrses.commonsapi.events;

import lombok.Getter;

@Getter
public class AccountCreatedEvent extends BaseEvent<String> {
    private Double initialBalance;
    private String currency;
    public AccountCreatedEvent(String id, Double initialBalance, String currency) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
    }
}
