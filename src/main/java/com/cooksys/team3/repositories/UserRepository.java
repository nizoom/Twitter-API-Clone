package com.cooksys.team3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.team3.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
