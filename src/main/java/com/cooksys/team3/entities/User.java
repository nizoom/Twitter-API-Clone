package com.cooksys.team3.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.cooksys.team3.embeddables.Credentials;
import com.cooksys.team3.embeddables.Profile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="user_table")
@Entity
@NoArgsConstructor
@Data
public class User {

	@Id
	@GeneratedValue
	private Long id;
	
	@Embedded
	private Profile profile;
	
	@Embedded
	private Credentials credentials;
	
	@Column(nullable=false)
	private Timestamp joined;
	
	private boolean deleted;
	
	@ManyToMany
	@JoinTable
	private List<User> followers;
	
	@ManyToMany(mappedBy="followers")
	private List<User> following;
}