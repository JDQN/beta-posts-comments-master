package com.posada.santiago.betapostsandcomments.application.handlers;


import com.posada.santiago.betapostsandcomments.business.gateways.model.ParticipantViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.PostViewModel;
import com.posada.santiago.betapostsandcomments.business.usecases.BringAllParticipant;
import com.posada.santiago.betapostsandcomments.business.usecases.BringAllPostsUseCase;
import com.posada.santiago.betapostsandcomments.business.usecases.BringParticipantById;
import com.posada.santiago.betapostsandcomments.business.usecases.BringPostById;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class QueryHandler {

    @Bean
    public RouterFunction<ServerResponse> bringAllPosts(BringAllPostsUseCase useCase) {
        return route(GET("/bring/all/posts"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.get(), PostViewModel.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> bringAllParticipants(BringAllParticipant useCase) {
        return route(GET("/bring/all/participants"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.get(), ParticipantViewModel.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> bringPostByIdHandler(BringPostById useCase) {
        return route(GET("/bring/post/{id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.pathVariable("id")), PostViewModel.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> bringParticipantByIdHandler(BringParticipantById useCase) {
        return route(GET("bring/participant/{id}"),
                request ->  ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.pathVariable("id")), ParticipantViewModel.class))
                );
    }
}
