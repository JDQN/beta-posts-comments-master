package com.posada.santiago.betapostsandcomments.application.config;


import com.posada.santiago.betapostsandcomments.application.handlers.QueueHandler;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String EXCHANGE = "core-posts";

    public static final String PROXY_QUEUE_POST_CREATED = "events.proxy.post.created";
    public static final String PROXY_QUEUE_COMMENT_ADDED = "events.proxy.comment.added";

    public static final String PROXY_QUEUE_POST_DELETED = "events.proxy.post.deleted";

    public static final String PROXY_QUEUE_POST_REACTION_ADDED = "events.proxy.post.reaction.added";


    public static final String GENERAL_QUEUE = "events.general";

    public static final String PROXY_ROUTING_KEY_POST_CREATED = "routingKey.proxy.post.created";
    public static final String PROXY_ROUTING_KEY_COMMENT_ADDED = "routingKey.proxy.comment.added";
    public static final String PROXY_ROUTING_KEY_POST_DELETED = "routingKey.proxy.post.deleted";

    public static final String PROXY_ROUTING_KEY_POST_REACTION_ADDED = "routingKey.proxy.post.reaction.added";



    @Autowired
    private QueueHandler handler;

    @Bean
    public Queue postCreatedQueue(){
        return new Queue(PROXY_QUEUE_POST_CREATED);
    }

    @Bean
    public Queue commentAddedQueue(){
        return new Queue(PROXY_QUEUE_COMMENT_ADDED);
    }

    @Bean
    public Queue postDeletedQueue(){ return new Queue(PROXY_QUEUE_POST_DELETED);}

    @Bean
    public Queue postReactionAddedQueue(){
        return new Queue(PROXY_QUEUE_POST_REACTION_ADDED);
    }

    @Bean
    public TopicExchange getTopicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding BindingToPostCreatedQueue() {
        return BindingBuilder.bind(postCreatedQueue()).to(getTopicExchange()).with(PROXY_ROUTING_KEY_POST_CREATED);
    }

    @Bean
    public Binding BindingToCommentAdded() {
        return BindingBuilder.bind(commentAddedQueue()).to(getTopicExchange()).with(PROXY_ROUTING_KEY_COMMENT_ADDED);
    }

    @Bean
    public Binding BindingToPostDeletedQueue(){
        return BindingBuilder.bind(postDeletedQueue()).to(getTopicExchange()).with(PROXY_ROUTING_KEY_POST_DELETED);
    }

    @Bean
    public Binding BindingToPostReactionAdded(){
        return BindingBuilder.bind(postReactionAddedQueue()).to(getTopicExchange()).with(PROXY_ROUTING_KEY_POST_REACTION_ADDED);

    }

    @RabbitListener(queues = GENERAL_QUEUE)
    public void listenToGeneralQueue(String received){
        handler.accept(received);
    }


}
