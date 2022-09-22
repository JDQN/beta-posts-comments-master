package com.posada.santiago.betapostsandcomments.business.usecases;

import com.posada.santiago.betapostsandcomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.business.gateways.EventBus;
import com.posada.santiago.betapostsandcomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.PostViewModel;
import com.posada.santiago.betapostsandcomments.business.generic.DomainUpdater;
import com.posada.santiago.betapostsandcomments.domain.events.CommentAdded;
import com.posada.santiago.betapostsandcomments.domain.events.PostCreated;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ViewUpdater extends DomainUpdater {

    private final DomainViewRepository repository;
    private final EventBus bus;

    public ViewUpdater(DomainViewRepository repository, EventBus bus){
        this.repository = repository;
        this.bus = bus;

        listen((PostCreated event)->{
            PostViewModel post = new PostViewModel(event.aggregateRootId(), event.getAuthor(), event.getTitle(), new ArrayList<>());
            bus.publishGeneric(post, "routingKey.proxy.post.created");
            repository.saveNewPost(post).subscribe();
        });
        listen((CommentAdded event)->{
            CommentViewModel comment = new CommentViewModel(event.getId(), event.aggregateRootId(), event.getAuthor(), event.getContent());
            repository.addCommentToPost(comment).subscribe();
            bus.publishGeneric(comment, "routingKey.proxy.comment.added");
        });
    }
}
