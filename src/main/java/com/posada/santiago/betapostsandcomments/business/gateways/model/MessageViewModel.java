package com.posada.santiago.betapostsandcomments.business.gateways.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MessageViewModel {
    // This is the Message Id
    private String id;
    //This is the aggregate Id
    private String participantId;
    private String name;
    private String content;
}
