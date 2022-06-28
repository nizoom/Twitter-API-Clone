package com.cooksys.team3.repositories;


import com.cooksys.team3.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository <Tweet, Long> {
}
