package com.posada.santiago.betapostsandcomments.application.handlers;


import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import com.posada.santiago.betapostsandcomments.application.adapters.bus.Notification;
import com.posada.santiago.betapostsandcomments.business.usecases.UpdateViewUseCase;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class QueueHandler implements Consumer<String> {
    private final Gson gson = new Gson();
    private final UpdateViewUseCase useCase;

    public QueueHandler(UpdateViewUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void accept(String received) {
        Notification notification = gson.fromJson(received, Notification.class);
        String type = notification.getType().replace("alpha.postandcomments", "posada.santiago.betapostsandcomments");
        try {
            DomainEvent event = (DomainEvent) gson.fromJson(notification.getBody(), Class.forName(type));
            useCase.accept(event);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
