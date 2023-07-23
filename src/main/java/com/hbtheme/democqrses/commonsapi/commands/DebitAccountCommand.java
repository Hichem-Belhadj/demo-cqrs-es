package com.hbtheme.democqrses.commonsapi.commands;

import lombok.Getter;

@Getter
public class DebitAccountCommand extends BaseCommand<String> {
    private Double amount;
    private String currency;
    public DebitAccountCommand(String id, Double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
