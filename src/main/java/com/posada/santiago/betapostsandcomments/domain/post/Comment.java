package com.posada.santiago.betapostsandcomments.domain.post;

import co.com.sofka.domain.generic.Entity;
import com.posada.santiago.betapostsandcomments.domain.commons.values.Content;
import com.posada.santiago.betapostsandcomments.domain.commons.values.Name;
import com.posada.santiago.betapostsandcomments.domain.commons.values.ParticipantId;
import com.posada.santiago.betapostsandcomments.domain.post.values.CommentId;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Comment extends Entity<CommentId> {


    private Name name;
    private Content content;

    private ParticipantId participantId;


    public Comment(CommentId entityId, Name name, Content content, ParticipantId participantId) {
        super(entityId);
        this.name = name;
        this.content = content;
        this.participantId = participantId;
    }

    public Name author() {
        return name;
    }

    public Content content() {
        return content;
    }

    public ParticipantId participantId() {
        return participantId;
    }
}
