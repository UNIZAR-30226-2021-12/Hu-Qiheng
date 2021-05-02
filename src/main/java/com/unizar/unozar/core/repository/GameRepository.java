package com.unizar.unozar.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unizar.unozar.core.entities.Game;

public interface GameRepository extends JpaRepository<Game, String>{

  Optional<Game> findByid(String id);
  
}
