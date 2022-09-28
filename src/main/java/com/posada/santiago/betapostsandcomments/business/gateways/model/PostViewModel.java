package com.posada.santiago.betapostsandcomments.business.gateways.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostViewModel {

	private String id;
	private String aggregateId;
	private String author;
	private String title;

	private String photoUrl;

	private String relevanceVote;

	private String participantId;
	private Boolean deleted;
	private List<CommentViewModel> comments;
	private List<String> reactions;

	private String creationDate;




    public PostViewModel() {
        this.comments = new ArrayList<>();
    }

	public PostViewModel(String aggregateId, String author, String title, String photoUrl, String relevanceVote, String participantId, Boolean deleted, List<CommentViewModel> comments, List<String> reactions, LocalDateTime creationDate) {
		this.aggregateId = aggregateId;
		this.author = author;
		this.title = title;
		this.photoUrl = photoUrl;
		this.relevanceVote = relevanceVote;
		this.participantId = participantId;
		this.deleted = deleted;
		this.comments = comments;
		this.reactions = reactions;
		this.creationDate = this.formatDate(creationDate);

	}

	private String formatDate(LocalDateTime creationDate){
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		String formattedDateTime = creationDate.format(formatter);
		return formattedDateTime;
	}
}
