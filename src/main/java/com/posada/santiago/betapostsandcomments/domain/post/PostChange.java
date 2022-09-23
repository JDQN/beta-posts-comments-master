package com.posada.santiago.betapostsandcomments.domain.post;

import co.com.sofka.domain.generic.EventChange;
import com.posada.santiago.betapostsandcomments.domain.commons.values.Content;
import com.posada.santiago.betapostsandcomments.domain.commons.values.Name;
import com.posada.santiago.betapostsandcomments.domain.commons.values.ParticipantId;
import com.posada.santiago.betapostsandcomments.domain.commons.values.PhotoUrl;
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

import java.util.ArrayList;


public class PostChange extends EventChange {

    public PostChange(Post post) {
        apply((PostCreated event) -> {
            post.title = new Title(event.getTitle());
            post.name = new Name(event.getName());
            post.photoUrl = new PhotoUrl(event.getPhotoUrl());
            post.participantId = ParticipantId.of(event.getParticipantId());
            post.reactions = new ArrayList<>();
            post.relevanceVote = new RelevanceVote("0");
            post.comments = new ArrayList<>();
            post.deleted = false;

        });

        apply((CommentAdded event) -> {
            Comment comment = new Comment(CommentId.of(event.getId()),
                    new Name(event.getAuthor()),
                    new Content(event.getContent()),
                    ParticipantId.of(event.getParticipantId()));
            post.comments.add(comment);
        });

        apply((CommentDeleted event) -> {
            post.comments.removeIf(comment -> comment.identity().equals(event.getCommentId()));
        });

        apply((PostDeleted event) -> {
            post.deleted = true;
        });

        apply((ReactionAdded event) -> {
            var reaction = new Reaction(event.getReaction());
            post.reactions.add(reaction);
        });

        apply((RelevanceVoteAdded event) -> {
            var relevanceVote = new RelevanceVote(event.getRelevanceVote());
            var newRelevance = Integer.parseInt(relevanceVote.value()) + Integer.parseInt(post.relevanceVote.value());
            post.relevanceVote = new RelevanceVote(String.valueOf(newRelevance));
        });
    }
}
