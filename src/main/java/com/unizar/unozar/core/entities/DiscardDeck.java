package com.unizar.unozar.core.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.unizar.unozar.core.exceptions.DiscardDeckNotEmpty;

@Entity
@Table(name = "DISCARD_DECK")
public class DiscardDeck{
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  
  @OrderColumn
  @OneToMany(targetEntity = Card.class, cascade = CascadeType.ALL)
  private Card deck[];
  
  @Column(name = "NUM_CARDS")
  private int numCards;
  
  public DiscardDeck(){
    deck = new Card[108];
    numCards = 0;
  }
  
  public void startDeck(Card top){
    if(numCards == 0){
      deck[numCards] = top;
      numCards++;
    }
    throw new DiscardDeckNotEmpty("The discard deck already has cards");
  }
  
  public boolean playCard(Card toPlay){
    if((numCards < 108) && isPlayable(toPlay)){
      deck[numCards] = toPlay;
      numCards++;
      return true;
    }
    return false;
  }
  
  private boolean isPlayable(Card toPlay){
    if(toPlay.isRelatedWith(deck[numCards - 1]) || 
        (toPlay.getColor() == Card.BLACK)){
      return true;
    }
    return false;
  }
  
  public Card[] emptyDeckButTop(){
    Card surplusDeck[] = new Card[numCards - 1];
    for(int i = 0; i < numCards - 1; i++){
      surplusDeck[i] = deck[i];
    }
    deck[0] = deck[numCards - 1];
    numCards = 1;
    return surplusDeck;
  }
  
  public int getNumCards(){
    return numCards;
  }
  
  public Card getTop(){
    if(numCards == 0){
      return null;
    }
    return deck[numCards - 1];
  }
}
