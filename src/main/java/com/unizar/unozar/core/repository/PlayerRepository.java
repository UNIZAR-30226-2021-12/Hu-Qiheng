package com.unizar.unozar.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unizar.unozar.core.entities.Player;

public interface PlayerRepository extends JpaRepository<Player, String>{
  // Functions to interact with Users from the database

  Optional<Player> findByEmail(String email);

}
