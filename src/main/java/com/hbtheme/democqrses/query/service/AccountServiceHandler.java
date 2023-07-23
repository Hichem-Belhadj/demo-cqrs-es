package com.hbtheme.democqrses.query.service;

import com.hbtheme.democqrses.commonsapi.enums.OperationType;
import com.hbtheme.democqrses.commonsapi.events.AccountActivatedEvent;
import com.hbtheme.democqrses.commonsapi.events.AccountCreatedEvent;
import com.hbtheme.democqrses.commonsapi.events.AccountCreditedEvent;
import com.hbtheme.democqrses.commonsapi.events.AccountDebitedEvent;
import com.hbtheme.democqrses.commonsapi.query.GetAccountByIdQuery;
import com.hbtheme.democqrses.commonsapi.query.GetAllAccountQuery;
import com.hbtheme.democqrses.query.models.Account;
import com.hbtheme.democqrses.query.models.Operation;
import com.hbtheme.democqrses.query.repository.AccountRepository;
import com.hbtheme.democqrses.query.repository.OperationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    @EventHandler
    public void on(AccountCreatedEvent event) {
        accountRepository.save(new Account(event.getId(), event.getInitialBalance(), event.getStatus(), event.getCurrency(), null));
        log.info("*********************************");
        log.info("AccountCreatedEvent received");
    }

    @EventHandler
    public void on(AccountActivatedEvent event) {
        Account account = accountRepository.findById(event.getId()).orElseThrow();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
        log.info("*********************************");
        log.info("AccountActivatedEvent received");
    }

    @EventHandler
    public void on(AccountDebitedEvent event) {
        Account account = accountRepository.findById(event.getId()).orElseThrow();
        account.setBalance(account.getBalance() - event.getAmount());
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        // ⚠️ This date should come from command.
        operation.setDate(new Date());
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        accountRepository.save(account);
        log.info("*********************************");
        log.info("AccountDebitedEvent received");
    }

    @EventHandler
    public void on(AccountCreditedEvent event) {
        Account account = accountRepository.findById(event.getId()).orElseThrow();
        account.setBalance(account.getBalance() + event.getAmount());
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        // ⚠️ This date should come from command.
        operation.setDate(new Date());
        operation.setType(OperationType.CREDIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        accountRepository.save(account);
        log.info("*********************************");
        log.info("AccountCreditedEvent received");
    }

    @QueryHandler
    public List<Account> on(GetAllAccountQuery query) {
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on(GetAccountByIdQuery query) {
        return accountRepository.findById(query.getId()).orElseThrow();
    }
}
