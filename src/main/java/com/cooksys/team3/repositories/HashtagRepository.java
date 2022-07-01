package com.cooksys.team3.repositories;

import com.cooksys.team3.entities.Hashtag;
import com.cooksys.team3.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashtagRepository extends JpaRepository <Hashtag, Long> {


	boolean existsByLabel(String label);


	@Query(value ="SELECT tweet FROM hashtag IN hashtag.tweets WHERE hashtag.label = ?1", nativeQuery = true)
	List<Tweet> getHashtagTweets(String label);


}
