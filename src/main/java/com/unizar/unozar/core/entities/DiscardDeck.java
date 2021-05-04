package com.unizar.unozar.core.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.unizar.unozar.core.Card;
import com.unizar.unozar.core.exceptions.DeckFull;
import com.unizar.unozar.core.exceptions.DiscardDeckEmpty;
import com.unizar.unozar.core.exceptions.DiscardDeckNotEmpty;

@Entity
@Table(name = "DISCARD_DECK")
public class DiscardDeck{
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  
  @ElementCollection
  private List<String> deck;
  
  public DiscardDeck(){
    deck = new ArrayList<String>();
  }
  
  public void startDeck(String top){
    if(deck.size() == 0){
      deck.add(top);
    }
    throw new DiscardDeckNotEmpty("The discard deck already has cards");
  }
  
  public boolean playCard(String toPlay){
    if(deck.size() >= 108){
      throw new DeckFull("PERO PERO PERO PERO COMO?!");
    }
    if(isPlayable(toPlay)){
      deck.add(toPlay);
      return true;
    }
    return false;
  }
  
  private boolean isPlayable(String toPlay){
    if(deck.size() == 0){
      throw new DiscardDeckEmpty("The discard deck is empty");
    }
    Card.checkCard(toPlay);
    if(Card.areCardsRelated(deck.get(deck.size() - 1), toPlay) || 
        (Card.isBlack(toPlay))){
      return true;
    }
    return false;
  }
  
  public String[] emptyDeckButTop(){
    String surplusDeck[] = new String[deck.size() - 1];
    for(int i = 0; i < deck.size() - 1; i++){
      surplusDeck[i] = deck.get(i);
      deck.remove(i);
    }
    return surplusDeck;
  }
  
  /////////////////////////
  // Getters and Setters //
  /////////////////////////
  
  public int getNumCards(){
    System.out.println("Hola desde getnumcards #X\n");
    return deck.size();
  }
  
  public String getTop(){
    if(deck.size() == 0){
      return null;
    }
    return deck.get(deck.size() - 1);
  }

}
