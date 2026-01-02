package com.backend.backend.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backend.entity.User;

@Repository
public interface Userepo extends JpaRepository<User,UUID> {

  Optional<User> findByEmail(String email);

 
  
}
