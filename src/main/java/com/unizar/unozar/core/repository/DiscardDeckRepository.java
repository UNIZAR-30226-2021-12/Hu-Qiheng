package com.unizar.unozar.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unizar.unozar.core.entities.DiscardDeck;

public interface DiscardDeckRepository extends 
    JpaRepository<DiscardDeck, Integer>{

}
