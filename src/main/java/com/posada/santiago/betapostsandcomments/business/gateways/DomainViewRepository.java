package com.posada.santiago.betapostsandcomments.business.gateways;

import com.posada.santiago.betapostsandcomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.EventViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.ParticipantViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.PostViewModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DomainViewRepository {
    Mono<PostViewModel> findByAggregateId(String aggregateId);
    Flux<PostViewModel> findAllPosts();
    Mono<PostViewModel> saveNewPost(PostViewModel post);

    Mono<ParticipantViewModel> saveNewParticipant(ParticipantViewModel participant);
    Mono<PostViewModel> addCommentToPost(CommentViewModel comment);

    Mono<ParticipantViewModel> addEventToParticipant(EventViewModel eventViewModel);
}
