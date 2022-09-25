package com.posada.santiago.betapostsandcomments.application.adapters.bus;


import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import com.posada.santiago.betapostsandcomments.application.config.RabbitMqConfig;
import com.posada.santiago.betapostsandcomments.business.gateways.EventBus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqEventBus implements EventBus {
    private final RabbitTemplate rabbitTemplate;
    private final Gson gson = new Gson();

    public RabbitMqEventBus(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(DomainEvent event) {
        var notification = new Notification(
                event.getClass().getTypeName(),
                gson.toJson(event)
        );

        if(notification.getType().contains("PostCreated")){
            rabbitTemplate.convertAndSend(
							RabbitMqConfig.EXCHANGE, RabbitMqConfig.PROXY_ROUTING_KEY_POST_CREATED, notification.serialize().getBytes()
            );
        }
        if(notification.getType().contains("CommentAdded")){
            rabbitTemplate.convertAndSend(
                    RabbitMqConfig.EXCHANGE, RabbitMqConfig.PROXY_ROUTING_KEY_COMMENT_ADDED, notification.serialize().getBytes()
            );
        }
        if(notification.getType().contains("PostDeleted")){
            rabbitTemplate.convertAndSend(
                    RabbitMqConfig.EXCHANGE, RabbitMqConfig.PROXY_ROUTING_KEY_POST_DELETED, notification.serialize().getBytes()
            );
        }

    }

    @Override
    public void publishGeneric(Object object, String routingKey) {
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE, routingKey, gson.toJson(object).getBytes()
        );
    }

    @Override
    public void publishError(Throwable errorEvent) {

    }
}
