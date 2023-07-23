package com.hbtheme.democqrses.commonsapi.events;

import com.hbtheme.democqrses.commonsapi.enums.AccountStatus;
import lombok.Getter;

@Getter
public class AccountActivatedEvent extends BaseEvent<String> {
    private AccountStatus status;
    public AccountActivatedEvent(String id, AccountStatus status) {
        super(id);
        this.status = status;
    }
}
