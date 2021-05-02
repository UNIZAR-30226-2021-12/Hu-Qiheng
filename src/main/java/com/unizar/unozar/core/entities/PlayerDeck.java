package com.unizar.unozar.core.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.unizar.unozar.core.exceptions.DeckFull;

@Entity
@Table(name = "PLAYER_DECK")
public class PlayerDeck{
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  
  @OrderColumn
  @OneToOne(targetEntity = Card.class, cascade = CascadeType.ALL)
  private Card deck[];
  
  @Column(name = "NUM_CARDS")
  private int numCards;
  
  public PlayerDeck(){
    deck = new Card[108];
    numCards = 0;
  }
  
  public void addCard(Card toAdd){
    if(numCards < 108){
      deck[numCards] = toAdd;
      numCards++;
    }
    throw new DeckFull("HOW?!?");
  }
  
  public Card select(int cardNum){
    if(cardNum < numCards){
      Card selected = deck[cardNum];
      for(int i = 0; i < numCards - cardNum; i++){
        deck[cardNum + i] = deck[cardNum + i + 1];
      }
      numCards--;
      return selected;
    }
    return null;
  }
  
  public int getNumCards(){
    return numCards;
  }
  
  @Override
  public String toString(){
    String cards = "";
    for(int i = 0; i < numCards; i++){
      cards += deck[i].toString();
    }
    return cards;
  }
}
