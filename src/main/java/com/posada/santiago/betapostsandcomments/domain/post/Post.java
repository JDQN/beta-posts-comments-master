package com.posada.santiago.betapostsandcomments.domain.post;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.betapostsandcomments.domain.commons.values.Content;
import com.posada.santiago.betapostsandcomments.domain.commons.values.Name;
import com.posada.santiago.betapostsandcomments.domain.commons.values.ParticipantId;
import com.posada.santiago.betapostsandcomments.domain.commons.values.PhotoUrl;
import com.posada.santiago.betapostsandcomments.domain.commons.values.PostId;
import com.posada.santiago.betapostsandcomments.domain.post.events.CommentAdded;
import com.posada.santiago.betapostsandcomments.domain.post.events.CommentDeleted;
import com.posada.santiago.betapostsandcomments.domain.post.events.PostCreated;
import com.posada.santiago.betapostsandcomments.domain.post.events.PostDeleted;
import com.posada.santiago.betapostsandcomments.domain.post.events.ReactionAdded;
import com.posada.santiago.betapostsandcomments.domain.post.events.RelevanceVoteAdded;
import com.posada.santiago.betapostsandcomments.domain.post.values.CommentId;
import com.posada.santiago.betapostsandcomments.domain.post.values.Reaction;
import com.posada.santiago.betapostsandcomments.domain.post.values.RelevanceVote;
import com.posada.santiago.betapostsandcomments.domain.post.values.Title;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Post extends AggregateEvent<PostId> {

    protected Title title;

    protected Name name;

    protected PhotoUrl photoUrl;

    protected List<Reaction> reactions;

    protected RelevanceVote relevanceVote;

    protected ParticipantId participantId;

    protected Boolean deleted;

    protected List<Comment> comments;

    public Post(PostId entityId, Title title, Name name, PhotoUrl photoUrl, ParticipantId participantId) {
        super(entityId);
        subscribe(new PostChange(this));
        appendChange(new PostCreated(title.value(), name.value(), photoUrl.value(), participantId.value()));
    }


    private Post(PostId id) {
        super(id);
        subscribe(new PostChange(this));
    }

    public static Post from(PostId id, List<DomainEvent> events) {
        Post post = new Post(id);
        events.forEach(event -> post.applyEvent(event));
        return post;
    }

    //----------- BEHAVIORS

    public void addAComment(CommentId id, Name name, Content content, ParticipantId participantId) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(content);
        Objects.requireNonNull(participantId);
        appendChange(new CommentAdded(id.value(), name.value(), content.value(), participantId.value())).apply();
    }

    public void addReaction(Reaction reaction) {
        Objects.requireNonNull(reaction);
        appendChange(new ReactionAdded(reaction.value())).apply();
    }

    public void addRelevanceVote(RelevanceVote relevanceVote) {
        Objects.requireNonNull(relevanceVote);
        appendChange(new RelevanceVoteAdded(relevanceVote.value())).apply();
    }

    public void deleteComment(CommentId commentId) {
        Objects.requireNonNull(commentId);
        appendChange(new CommentDeleted(commentId.value())).apply();
    }

    public void deletePost(PostId postId) {
        Objects.requireNonNull(postId);
        appendChange(new PostDeleted(postId.value())).apply();
    }

    //------------- FINDER

    public Optional<Comment> getCommentById(CommentId commentId) {
        return comments.stream().filter((comment -> comment.identity().equals(commentId))).findFirst();
    }


}
