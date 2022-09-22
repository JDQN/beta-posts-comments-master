package com.posada.santiago.betapostsandcomments.business.usecases;


import com.posada.santiago.betapostsandcomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.business.gateways.model.PostViewModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
public class BringAllPostsUseCase implements Supplier<Flux<PostViewModel>> {
    private final DomainViewRepository repository;

    public BringAllPostsUseCase(DomainViewRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<PostViewModel> get() {
        return repository.findAllPosts();
    }

}
