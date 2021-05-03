package com.unizar.unozar.core.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.unizar.unozar.core.exceptions.DiscardDeckNotEmpty;

@Entity
@Table(name = "DISCARD_DECK")
public class DiscardDeck{
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  
  @OneToMany(mappedBy = "discardDeck", cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Card> deck;
  
  @JoinColumn(name = "GAME_ID")
  @OneToOne
  private Game game;
  
  public DiscardDeck(){
    deck = new ArrayList<Card>();
  }
  
  public void startDeck(Card top){
    if(deck.size() == 0){
      deck.add(top);
    }
    throw new DiscardDeckNotEmpty("The discard deck already has cards");
  }
  
  public boolean playCard(Card toPlay){
    if((deck.size() < 108) && isPlayable(toPlay)){
      deck.add(toPlay);
      return true;
    }
    return false;
  }
  
  private boolean isPlayable(Card toPlay){
    if(toPlay.isRelatedWith(deck.get(deck.size() - 1)) || 
        (toPlay.getColor() == Card.BLACK)){
      return true;
    }
    return false;
  }
  
  public Card[] emptyDeckButTop(){
    Card surplusDeck[] = new Card[deck.size() - 1];
    for(int i = 0; i < deck.size() - 1; i++){
      surplusDeck[i] = deck.get(i);
      deck.remove(i);
    }
    return surplusDeck;
  }
  
  public int getNumCards(){
    return deck.size();
  }
  
  public Card getTop(){
    if(deck.size() == 0){
      return null;
    }
    return deck.get(deck.size() - 1);
  }
}
