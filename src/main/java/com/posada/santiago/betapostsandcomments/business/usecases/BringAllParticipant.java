package com.posada.santiago.betapostsandcomments.business.usecases;

import com.posada.santiago.betapostsandcomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.business.gateways.model.ParticipantViewModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
public class BringAllParticipant implements Supplier<Flux<ParticipantViewModel>> {

    private final DomainViewRepository repository;

    public BringAllParticipant(DomainViewRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<ParticipantViewModel> get() {
        return repository.findAllParticipants();
    }
}
