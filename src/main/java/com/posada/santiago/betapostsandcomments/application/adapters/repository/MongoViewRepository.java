package com.posada.santiago.betapostsandcomments.application.adapters.repository;


import com.google.gson.Gson;
import com.posada.santiago.betapostsandcomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.PostViewModel;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class MongoViewRepository implements DomainViewRepository {
    private final ReactiveMongoTemplate template;

    private final Gson gson = new Gson();

    public MongoViewRepository(ReactiveMongoTemplate template) {
        this.template = template;
    }

    @Override
    public Mono<PostViewModel> findByAggregateId(String aggregateId) {
        var query = new Query(Criteria.where("aggregateId").is(aggregateId));
        return template.findOne(query, PostViewModel.class);
    }

    @Override
    public Flux<PostViewModel> findAllPosts() {
        return template.findAll(PostViewModel.class);
    }

    @Override
    public Mono<PostViewModel> saveNewPost(PostViewModel post) {
        return template.save(post);
    }

    @Override
    public Mono<PostViewModel> addCommentToPost(CommentViewModel comment) {
        var query = new Query(Criteria.where("aggregateId").is(comment.getPostId()));
        Update update = new Update();
        return template.findOne(query, PostViewModel.class)
                .flatMap(postViewModel -> {
                    List<CommentViewModel> comments = postViewModel.getComments();
                    comments.add(comment);
                    update.set("comments", comments);
                    return template.findAndModify(query, update, PostViewModel.class);
                });
    }
}
