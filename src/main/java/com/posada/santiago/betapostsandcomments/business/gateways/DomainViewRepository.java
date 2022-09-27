package com.posada.santiago.betapostsandcomments.business.gateways;

import com.posada.santiago.betapostsandcomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.EventViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.MessageViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.ParticipantViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.PostViewModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DomainViewRepository {
    Mono<PostViewModel> findByAggregateId(String aggregateId);

    Mono<ParticipantViewModel> findParticipantById(String aggregateId);

    Flux<PostViewModel> findAllPosts();

    Flux<ParticipantViewModel> findAllParticipants();

    Mono<PostViewModel> saveNewPost(PostViewModel post);

    Mono<PostViewModel> deletePost(String postId);

    Mono<ParticipantViewModel> saveNewParticipant(ParticipantViewModel participant);

    Mono<PostViewModel> addCommentToPost(CommentViewModel comment);

    Mono<ParticipantViewModel> addMessageToParticipant(MessageViewModel message);

    Mono<ParticipantViewModel> AddFavorite(String postId, String participantId);


    Mono<ParticipantViewModel> addEventToParticipant(EventViewModel eventViewModel);

    Mono<PostViewModel> addReactions(String reaction, String postId);

    Mono<PostViewModel> updateRelevanceVote(String relevanteVote, String postId);

	 Mono <PostViewModel> deleteCommenPost(String commentId, String postId);
}
