package com.cooksys.team3.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cooksys.team3.entities.Hashtag;
import com.cooksys.team3.entities.Tweet;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

	boolean existsByLabel(String label);

	Optional<Hashtag> findAllByLabel(String label);

	@Query(value = "SELECT tweet FROM hashtag IN hashtag.tweets WHERE hashtag.label = ?1", nativeQuery = true)
	List<Tweet> getHashtagTweets(String label);

	Optional<Hashtag> findByLabel(String hashtag);

}
