package com.cooksys.team3.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository <Tweet, Long> {
}
