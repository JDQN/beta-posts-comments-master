package com.posada.santiago.betapostsandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.betapostsandcomments.business.gateways.EventBus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Service
public class UpdateViewUseCase implements Consumer<DomainEvent> {

    private final EventBus bus;
    private final ViewUpdater updater;

    public UpdateViewUseCase(EventBus bus, ViewUpdater updater) {
        this.bus = bus;
        this.updater = updater;
    }

    @Override
    public void accept(DomainEvent domainEvent) {
        updater.applyEvent(domainEvent);
    }
}
