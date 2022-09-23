package com.posada.santiago.betapostsandcomments.domain.commons.values;

import co.com.sofka.domain.generic.Identity;

public class ParticipantId extends Identity {

    private ParticipantId(String uuid){
        super(uuid);
    }

    public ParticipantId(){

    }

    public static ParticipantId of(String uuid){
        return new ParticipantId(uuid);
    }
}
