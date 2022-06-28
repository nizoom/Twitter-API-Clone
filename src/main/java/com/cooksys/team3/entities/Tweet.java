package com.cooksys.team3.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Tweet {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_table_id")
	@Column(nullable = false)
	private int author;
	
	@Column(nullable = false, updatable = false)
	private Timestamp posted;
	
	private boolean deleted;
	
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "tweet_id")
	private int inReplyTo;
	
	@OneToMany(mappedBy = "inReplyTo")
	private List<Tweet> replyTweets;
	
	@ManyToOne
	@JoinColumn(name = "tweet_id")
	private int repostOf;
	
	@OneToMany(mappedBy = "repostOf")
	private List<Tweet> repostTweets;
	
	@ManyToMany
	@JoinTable
	private List<Hashtag> hashtags;
	
	// There are two ManyToMany relationships between Tweet and User: user_likes and user_mentions
	@ManyToMany
	@JoinTable
	private List<User> userLikes;
	
	@ManyToMany
	@JoinTable
	private List<User> userMentions;
}
