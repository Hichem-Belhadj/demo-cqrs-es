package com.hbtheme.democqrses.commonsapi.events;

import lombok.Getter;

@Getter
public class AccountCreditedEvent extends BaseEvent<String> {
    private Double amount;
    private String currency;
    public AccountCreditedEvent(String id, Double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
