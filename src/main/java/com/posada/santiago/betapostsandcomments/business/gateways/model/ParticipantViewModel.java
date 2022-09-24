package com.posada.santiago.betapostsandcomments.business.gateways.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class ParticipantViewModel {

    private String id;
    private String aggregateId;
    private String name;
    private String photoUrl;
    private String rol;
    private List<MessageViewModel> messages;
    private List<EventViewModel> events;
    private List<String> favoritesId;
    private List<PostViewModel> favorites;

    public ParticipantViewModel(String aggregateId, String name, String photoUrl, String rol,
                                List<MessageViewModel> messages, List<EventViewModel> events,
                                List<String> favoritesId, List<PostViewModel> favorites) {
        this.aggregateId = aggregateId;
        this.name = name;
        this.photoUrl = photoUrl;
        this.rol = rol;
        this.messages = messages;
        this.events = events;
        this.favoritesId = favoritesId;
        this.favorites = favorites;
    }
}
