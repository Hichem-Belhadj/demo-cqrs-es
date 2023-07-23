package com.hbtheme.democqrses.commands.controllers;

import com.hbtheme.democqrses.commonsapi.commands.CreateAccountCommand;
import com.hbtheme.democqrses.commonsapi.commands.CreditAccountCommand;
import com.hbtheme.democqrses.commonsapi.commands.DebitAccountCommand;
import com.hbtheme.democqrses.commonsapi.dto.CreateAccountRequestDto;
import com.hbtheme.democqrses.commonsapi.dto.CreditAccountRequestDto;
import com.hbtheme.democqrses.commonsapi.dto.DebitAccountRequestDto;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * ⚠ℹ️The autowired annotation accesses the attribute directly,
 * which violates the encapsulation principle. To avoid this,
 * we use constructors with the Lombok AllArgsConstructor annotation.
 */

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;
    @PostMapping(path = "/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDto request) {
        String ID = UUID.randomUUID().toString();
        // Send event to Axon control bus
        return commandGateway.send(new CreateAccountCommand(ID, request.getInitialBalance(), request.getCurrency()));
    }
    @PutMapping(path = "/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDto request) {
        return commandGateway.send(new CreditAccountCommand(request.getAccountId(), request.getAmount(), request.getCurrency()));
    }
    @PutMapping(path = "/debit")
    public CompletableFuture<String> creditAccount(@RequestBody DebitAccountRequestDto request) {
        return commandGateway.send(new DebitAccountCommand(request.getAccountId(), request.getAmount(), request.getCurrency()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId) {
        return eventStore.readEvents(accountId).asStream();
    }
}
