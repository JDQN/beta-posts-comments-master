package com.posada.santiago.betapostsandcomments.business.gateways;

import co.com.sofka.domain.generic.DomainEvent;

public interface EventBus<T> {
    void publish(DomainEvent event);

    void publishGeneric(T object, String routingKey);

    void publishError(Throwable errorEvent);
}
