package com.posada.santiago.betapostsandcomments.business.gateways.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostViewModel {

    private String id;
    private String aggregateId;
    private String author;
    private String title;
    protected Boolean deleted;
    private List<CommentViewModel> comments;

    public PostViewModel() {
        this.comments = new ArrayList<>();
    }

    public PostViewModel(String aggregateId, String author, String title, List<CommentViewModel> comments) {
        this.aggregateId = aggregateId;
        this.author = author;
        this.title = title;
        this.deleted = false;
        this.comments = comments;
    }
}
