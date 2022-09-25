package com.posada.santiago.betapostsandcomments.business.gateways.model;

import com.posada.santiago.betapostsandcomments.domain.commons.values.ParticipantId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentViewModel {
    private String id;
    private String postId;
    private String author;
    private String content;
    private String participantId;

}
