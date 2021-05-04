package com.unizar.unozar.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unizar.unozar.core.entities.PlayerDeck;

public interface PlayerDeckRepository extends 
    JpaRepository<PlayerDeck, Integer>{

}
