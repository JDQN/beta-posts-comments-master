package com.posada.santiago.betapostsandcomments.domain.values;

import co.com.sofka.domain.generic.Identity;

public class PostId extends Identity {
    private PostId(String uuid) {
        super(uuid);
    }

    public PostId() {
    }

    public static PostId of(String uuid){
        return new PostId(uuid);
    }
}
