package com.posada.santiago.betapostsandcomments.business.usecases;

import com.posada.santiago.betapostsandcomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.business.gateways.EventBus;
import com.posada.santiago.betapostsandcomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.EventViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.ParticipantViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.PostViewModel;
import com.posada.santiago.betapostsandcomments.business.generic.DomainUpdater;
import com.posada.santiago.betapostsandcomments.domain.participant.events.EventCasted;
import com.posada.santiago.betapostsandcomments.domain.participant.events.ParticipantCreated;
import com.posada.santiago.betapostsandcomments.domain.participant.values.Detail;
import com.posada.santiago.betapostsandcomments.domain.participant.values.Element;
import com.posada.santiago.betapostsandcomments.domain.participant.values.TypeOfEvent;
import com.posada.santiago.betapostsandcomments.domain.post.events.CommentAdded;
import com.posada.santiago.betapostsandcomments.domain.post.events.PostCreated;
import com.posada.santiago.betapostsandcomments.domain.post.events.PostDeleted;
import com.posada.santiago.betapostsandcomments.domain.post.events.ReactionAdded;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class ViewUpdater extends DomainUpdater {

    private final DomainViewRepository repository;
    private final EventBus bus;

    public ViewUpdater(DomainViewRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;

        listen((PostCreated event) -> {
            PostViewModel post = new PostViewModel(
                    event.aggregateRootId(),
                    event.getName(),
                    event.getTitle(),
                    event.getPhotoUrl(),
                    "0",
                    event.getParticipantId(),
                    false,
                    new ArrayList<>(), new ArrayList<>()
            );
            bus.publishGeneric(post, "routingKey.proxy.post.created");
            EventViewModel eventViewModel = new EventViewModel(
                    String.valueOf(Math.random()),
                    event.getParticipantId(),
                    LocalDateTime.now().toString(),
                    "Canal",
                    "Creado",
                    event.getTitle()
            );
            repository.addEventToParticipant(eventViewModel).subscribe();
            repository.saveNewPost(post).subscribe();
        });
        listen((ParticipantCreated event) -> {
            ParticipantViewModel participant = new ParticipantViewModel(
                    event.aggregateRootId(),
                    event.getName(),
                    event.getPhotoUrl(),
                    event.getRol(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            repository.saveNewParticipant(participant).subscribe();
        });

        listen((PostDeleted event) -> {
            var postId = event.getPostId();
            repository.deletePost(postId).subscribe();
            bus.publishGeneric(postId, "routingKey.proxy.post.deleted");
        });

        listen((CommentAdded event) -> {
            CommentViewModel comment = new CommentViewModel(
                    event.getId(),
                    event.aggregateRootId(),
                    event.getAuthor(),
                    event.getContent(),
                    event.getParticipantId());
            EventViewModel eventViewModel = new EventViewModel(
                    String.valueOf(Math.random()),
                    event.getParticipantId(),
                    LocalDateTime.now().toString(),
                    "Comentario",
                    "Creado",
                    event.getContent()
            );
            repository.addEventToParticipant(eventViewModel).subscribe();
            repository.addCommentToPost(comment).subscribe();
            bus.publishGeneric(comment, "routingKey.proxy.comment.added");
        });
        listen((EventCasted event) -> {
            EventViewModel eventViewModel = new EventViewModel(
                    event.getEventId(),
                    event.aggregateRootId(),
                    event.getDate(),
                    event.getElement(),
                    event.getTypeOfEvent(),
                    event.getDetail()
            );
            repository.addEventToParticipant(eventViewModel).subscribe();
            //Add bus publisher just if this need a websocket
        });
        listen((ReactionAdded event) -> {
            repository.addReactions(event.getReaction(), event.aggregateRootId()).subscribe();
            //Add post publisher
        });
    }
}
