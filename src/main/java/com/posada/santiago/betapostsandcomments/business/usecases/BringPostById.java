package com.posada.santiago.betapostsandcomments.business.usecases;


import com.posada.santiago.betapostsandcomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.business.gateways.model.PostViewModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class BringPostById implements Function<String, Mono<PostViewModel>> {

    private final DomainViewRepository repository;

    public BringPostById(DomainViewRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<PostViewModel> apply(String id) {
        return repository.findByAggregateId(id);
    }
}
