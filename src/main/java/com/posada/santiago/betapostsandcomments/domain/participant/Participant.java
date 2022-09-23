package com.posada.santiago.betapostsandcomments.domain.participant;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.betapostsandcomments.domain.commons.values.Content;
import com.posada.santiago.betapostsandcomments.domain.commons.values.Name;
import com.posada.santiago.betapostsandcomments.domain.commons.values.ParticipantId;
import com.posada.santiago.betapostsandcomments.domain.commons.values.PhotoUrl;
import com.posada.santiago.betapostsandcomments.domain.commons.values.PostId;
import com.posada.santiago.betapostsandcomments.domain.participant.events.EventCasted;
import com.posada.santiago.betapostsandcomments.domain.participant.events.FavAdded;
import com.posada.santiago.betapostsandcomments.domain.participant.events.MessageReceived;
import com.posada.santiago.betapostsandcomments.domain.participant.events.ParticipantCreated;
import com.posada.santiago.betapostsandcomments.domain.participant.values.DateOfEvent;
import com.posada.santiago.betapostsandcomments.domain.participant.values.Detail;
import com.posada.santiago.betapostsandcomments.domain.participant.values.Element;
import com.posada.santiago.betapostsandcomments.domain.participant.values.EventId;
import com.posada.santiago.betapostsandcomments.domain.participant.values.MessageId;
import com.posada.santiago.betapostsandcomments.domain.participant.values.Rol;
import com.posada.santiago.betapostsandcomments.domain.participant.values.Type;

import java.util.List;
import java.util.Objects;

public class Participant extends AggregateEvent<ParticipantId> {

    protected Name name;
    protected PhotoUrl photoUrl;
    protected Rol rol;
    protected List<Message> messages;
    protected List<Event> events;
    protected List<PostId> favorites;

    public Participant(ParticipantId entityId, Name name, PhotoUrl photoUrl, Rol rol) {
        super(entityId);
        subscribe(new ParticipantChange(this));
        appendChange(new ParticipantCreated(name.value(), photoUrl.value(), rol.value()));
    }

    private Participant(ParticipantId id) {
        super(id);
        subscribe(new ParticipantChange(this));
    }

    public static Participant from(ParticipantId id, List<DomainEvent> events) {
        Participant participant = new Participant(id);
        events.forEach(event -> participant.applyEvent(event));
        return participant;
    }

    //----------- BEHAVIORS

    public void addFav(PostId postId) {
        Objects.requireNonNull(postId);
        appendChange(new FavAdded(postId.value())).apply();
    }

    public void castEvent(EventId eventId, DateOfEvent date, Element element, Type type, Detail detail) {
        Objects.requireNonNull(eventId);
        Objects.requireNonNull(date);
        Objects.requireNonNull(element);
        Objects.requireNonNull(type);
        Objects.requireNonNull(detail);
        appendChange(new EventCasted(eventId.value(), date.value(),element.value(),type.value(),detail.value())).apply();
    }

    public void receiveMessage(MessageId messageId, Name name, Content content){
        Objects.requireNonNull(messageId);
        Objects.requireNonNull(name);
        Objects.requireNonNull(content);
        appendChange(new MessageReceived(messageId.value(), name.value(), content.value())).apply();
    }

}
