package com.posada.santiago.betapostsandcomments.domain.commands;

import co.com.sofka.domain.generic.Command;

public class CreatePostCommand extends Command {
    private String postId;
    private String author;
    private String title;

    public CreatePostCommand() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
