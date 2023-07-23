package com.hbtheme.democqrses.commonsapi.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Here we use a generic type for the ID so as not to be limited by the type.
 * Commands and events are immutable objects, which is why no setter is defined.
 * @param <T>
 */
public abstract class BaseCommand<T> {
    // Axon annotation to define aggregate ID.
    @TargetAggregateIdentifier
    @Getter
    private T id;

    public BaseCommand(T id) {
        this.id = id;
    }
}
