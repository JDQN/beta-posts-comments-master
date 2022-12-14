package com.posada.santiago.betapostsandcomments.domain.participant;

import co.com.sofka.domain.generic.Entity;
import com.posada.santiago.betapostsandcomments.domain.participant.values.DateOfEvent;
import com.posada.santiago.betapostsandcomments.domain.participant.values.Detail;
import com.posada.santiago.betapostsandcomments.domain.participant.values.Element;
import com.posada.santiago.betapostsandcomments.domain.participant.values.EventId;
import com.posada.santiago.betapostsandcomments.domain.participant.values.TypeOfEvent;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode

public class Event extends Entity<EventId> {

    private DateOfEvent dateOfEvent;
    private Element element;
    private TypeOfEvent typeOfEvent;
    private Detail detail;

    public Event(EventId entityId, DateOfEvent dateOfEvent, Element element, TypeOfEvent typeOfEvent, Detail detail) {
        super(entityId);
        this.dateOfEvent = dateOfEvent;
        this.element = element;
        this.typeOfEvent = typeOfEvent;
        this.detail = detail;
    }

    public DateOfEvent dateOfEvent() {
        return dateOfEvent;
    }

    public Element element() {
        return element;
    }

    public TypeOfEvent typeOfEvent() {
        return typeOfEvent;
    }

    public Detail detail() {
        return detail;
    }
}
