package com.posada.santiago.betapostsandcomments.business.generic;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public abstract class DomainUpdater {

    protected Set<Consumer<? super DomainEvent>> listeners = new HashSet();

    public DomainUpdater() {
    }

    protected void listen(Consumer<? extends DomainEvent> changeEvent) {
        this.listeners.add((Consumer<? super DomainEvent>) changeEvent);
    }

    public final void applyEvent(DomainEvent domainEvent) {
        this.listeners.forEach((consumer) -> {
            try {
                consumer.accept(domainEvent);
            } catch (ClassCastException var6) {
            }
        });
    }
}
