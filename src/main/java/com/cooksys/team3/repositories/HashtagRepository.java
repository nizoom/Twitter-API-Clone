package com.cooksys.team3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.team3.entities.Hashtag;

@Repository
public interface HashtagRepository extends JpaRepository <Hashtag, Long> {
	
	boolean existsByLabel(String label);
}
