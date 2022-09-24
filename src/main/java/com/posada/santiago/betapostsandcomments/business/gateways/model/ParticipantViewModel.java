package com.posada.santiago.betapostsandcomments.business.gateways.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
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

}
