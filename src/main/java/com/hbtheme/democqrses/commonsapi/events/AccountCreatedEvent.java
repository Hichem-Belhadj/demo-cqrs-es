package com.hbtheme.democqrses.commonsapi.events;

import com.hbtheme.democqrses.commonsapi.enums.AccountStatus;
import lombok.Getter;

@Getter
public class AccountCreatedEvent extends BaseEvent<String> {
    private Double initialBalance;
    private String currency;
    private AccountStatus status;
    public AccountCreatedEvent(String id, Double initialBalance, String currency, AccountStatus status) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
        this.status = status;
    }
}
