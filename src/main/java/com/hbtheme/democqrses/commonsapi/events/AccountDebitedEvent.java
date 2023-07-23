package com.hbtheme.democqrses.commonsapi.events;

import lombok.Getter;

@Getter
public class AccountDebitedEvent extends BaseEvent<String> {
    private Double amount;
    private String currency;
    public AccountDebitedEvent(String id, Double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
