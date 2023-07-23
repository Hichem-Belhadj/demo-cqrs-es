package com.hbtheme.democqrses.commands.aggregates;

import com.hbtheme.democqrses.commonsapi.commands.CreateAccountCommand;
import com.hbtheme.democqrses.commonsapi.commands.CreditAccountCommand;
import com.hbtheme.democqrses.commonsapi.commands.DebitAccountCommand;
import com.hbtheme.democqrses.commonsapi.enums.AccountStatus;
import com.hbtheme.democqrses.commonsapi.events.AccountActivatedEvent;
import com.hbtheme.democqrses.commonsapi.events.AccountCreatedEvent;
import com.hbtheme.democqrses.commonsapi.events.AccountCreditedEvent;
import com.hbtheme.democqrses.commonsapi.events.AccountDebitedEvent;
import com.hbtheme.democqrses.commonsapi.exceptions.AmountNegativeException;
import com.hbtheme.democqrses.commonsapi.exceptions.BalanceNotSufficientException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

/**
 *
 * ℹ️ An aggregate must have a constructor with no parameters.
 * ℹ️
 */
@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private Double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
        // Required by AXON
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        // Implementing business logic
        if (command.getInitialBalance() < 0) {
            throw new RuntimeException("Unable to create an account with a negative balance!");
        }
        AggregateLifecycle.apply(
                new AccountCreatedEvent(command.getId(), command.getInitialBalance(), command.getCurrency())
        );
    }

    @CommandHandler
    public void handle(CreditAccountCommand command) {
        if (command.getAmount() < 0) {
            throw new AmountNegativeException("Amount should not be negative!");
        }
        AggregateLifecycle.apply(
                new AccountCreditedEvent(command.getId(), command.getAmount(), command.getCurrency())
        );
    }

    @CommandHandler
    public void handle(DebitAccountCommand command) {
        if (command.getAmount() < 0) {
            throw new AmountNegativeException("Amount should not be negative!");
        }
        if (this.balance < command.getAmount()) {
            throw new BalanceNotSufficientException(String.format("Balance not sufficient! => %s", this.balance));
        }
        AggregateLifecycle.apply(
                new AccountDebitedEvent(command.getId(), command.getAmount(), command.getCurrency())
        );
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getId();
        this.balance = event.getInitialBalance();
        this.currency = event.getCurrency();
        this.status = AccountStatus.CREATED;
        AggregateLifecycle.apply(new AccountActivatedEvent(event.getId(), AccountStatus.ACTIVATED));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event) {
        this.status = event.getStatus();
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        this.balance += event.getAmount();
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        this.balance -= event.getAmount();
    }
}
