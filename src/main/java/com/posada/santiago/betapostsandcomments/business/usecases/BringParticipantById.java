package com.posada.santiago.betapostsandcomments.business.usecases;

import com.posada.santiago.betapostsandcomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.business.gateways.model.ParticipantViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.PostViewModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class BringParticipantById implements Function<String, Mono<ParticipantViewModel>> {

    private final DomainViewRepository repository;

    @Override
    public Mono<ParticipantViewModel> apply(String id) {
        return repository.findParticipantById(id);
    }
}
