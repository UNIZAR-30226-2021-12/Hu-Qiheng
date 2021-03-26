package com.unizar.unozar.core.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unizar.unozar.core.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {
  // Functions to interact with Users from the database

  User findByEmail(String email);

}
