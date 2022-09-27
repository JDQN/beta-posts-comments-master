package com.posada.santiago.betapostsandcomments.application.adapters.repository;


import com.google.gson.Gson;
import com.posada.santiago.betapostsandcomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.EventViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.MessageViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.ParticipantViewModel;
import com.posada.santiago.betapostsandcomments.business.gateways.model.PostViewModel;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    public Mono<ParticipantViewModel> findParticipantById(String aggregateId) {
        var query = new Query(Criteria.where("aggregateId").is(aggregateId));
        return template.findOne(query, ParticipantViewModel.class);
    }

    @Override
    public Flux<PostViewModel> findAllPosts() {
        return template.findAll(PostViewModel.class);
    }

    @Override
    public Flux<ParticipantViewModel> findAllParticipants() {
        return template.findAll(ParticipantViewModel.class);
    }

    @Override
    public Mono<PostViewModel> saveNewPost(PostViewModel post) {
        return template.save(post);
    }

    @Override
    public Mono<PostViewModel> deletePost(String postId) {
        var query = new Query(Criteria.where("aggregateId").is(postId));
        return template.findAndRemove(query,PostViewModel.class);
    }

    @Override
    public Mono<ParticipantViewModel> saveNewParticipant(ParticipantViewModel participant) {
        return template.save(participant);
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

    @Override
    public Mono<ParticipantViewModel> addMessageToParticipant(MessageViewModel message) {
        var query = new Query(Criteria.where("aggregateId").is(message.getParticipantId()));
        Update update = new Update();
        return template.findOne(query, ParticipantViewModel.class)
                .flatMap(participantViewModel -> {
                    List<MessageViewModel> messages = participantViewModel.getMessages();
                    messages.add(message);
                    update.set("messages", messages);
                    return template.findAndModify(query, update, ParticipantViewModel.class);
                });
    }

    @Override
    public Mono<PostViewModel> addReactions(String reaction, String postId) {
        var query = new Query(Criteria.where("aggregateId").is(postId));
        Update update = new Update();
        return template.findOne(query, PostViewModel.class)
                .flatMap(postViewModel -> {
                    List<String> reactions = postViewModel.getReactions();
                    reactions.add(reaction);
                    update.set("reactions", reactions);
                    return template.findAndModify(query, update, PostViewModel.class);
                });
    }

    @Override
    public Mono<PostViewModel> updateRelevanceVote(String relevanteVote, String postId) {
        var query = new Query(Criteria.where("aggregateId").is(postId));
        Update update = new Update();
        return template.findOne(query, PostViewModel.class)
						.flatMap(postViewModel -> {
								String currentvoteRelevant = postViewModel.getRelevanceVote();
								var newRelevantVote = Integer.parseInt(currentvoteRelevant) + Integer.parseInt(relevanteVote);
								update.set("relevanceVote", newRelevantVote);
								return template.findAndModify(query, update, PostViewModel.class);
						});
    }


	@Override
	public Mono<PostViewModel> deleteCommenPost(String commentId, String postId ) {
			System.out.println("deleteCommenPost");
			var query = new Query(Criteria.where("aggregateId").is(postId));
			Update update = new Update();
			return template.findOne(query, PostViewModel.class)
				 .flatMap(postViewModel -> {
					 System.out.println("deleteCommenPost-123");
					 List<CommentViewModel> comments = postViewModel.getComments();
					 comments.removeIf(comment -> comment.getId().equals(commentId));
					 update.set("comments", comments);
					 return template.findAndModify(query, update, PostViewModel.class);
				 });
	}


	@Override
    public Mono<ParticipantViewModel> AddFavorite(String postId, String participantId) {
        var query = new Query(Criteria.where("aggregateId").is(participantId));
        Update update = new Update();
        return template.findOne(query, ParticipantViewModel.class)
						.flatMap(participantViewModel -> {
								var listaFavoritos = participantViewModel.getFavoritesId();
								listaFavoritos.add(postId);
								update.set("favoritesId", listaFavoritos);
								return template.findAndModify(query, update, ParticipantViewModel.class);
						});
    }

    @Override
    public Mono<ParticipantViewModel> addEventToParticipant(EventViewModel eventViewModel) {
        var query = new Query(Criteria.where("aggregateId").is(eventViewModel.getParticipantId()));
        Update update = new Update();
        return template.findOne(query, ParticipantViewModel.class)
						.flatMap(participantViewModel -> {
								List<EventViewModel> events = participantViewModel.getEvents();
								events.add(eventViewModel);
								update.set("events", events);
								return template.findAndModify(query, update, ParticipantViewModel.class);
						});
    }

}
