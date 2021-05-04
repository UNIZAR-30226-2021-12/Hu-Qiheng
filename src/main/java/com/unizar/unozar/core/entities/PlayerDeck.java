package com.unizar.unozar.core.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.unizar.unozar.core.Card;
import com.unizar.unozar.core.exceptions.CardNotFound;
import com.unizar.unozar.core.exceptions.DeckFull;

@Entity
@Table(name = "PLAYER_DECK")
public class PlayerDeck{
  
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @ElementCollection
  private List<String> deck;
  
  @ManyToOne()
  @JoinColumn(name = "GAME_ID")
  private Game game;
  
  public PlayerDeck(){
    deck = new ArrayList<String>();
  }
  
  public void addCard(String toAdd){
    Card.checkCard(toAdd);
    if(deck.size() >= 108){
      throw new DeckFull("HOW?!?");
    }
    deck.add(toAdd);
  }
  
  public String select(int cardNum){
    if(cardNum >= deck.size()){
      throw new CardNotFound("The player does not have that many cards");
    }
    String selected = deck.get(cardNum);
    deck.remove(cardNum);
    return selected;
  }
  
  /////////////////////////
  // Getters and Setters //
  /////////////////////////
  
  public void setGame(Game game){
    this.game = game;
  }
  
  public int getNumCards(){
    return deck.size();
  }
  
  public String[] getCards(){
    return deck.toArray(new String[0]);
  }
  
  //////////////
  // Override //
  //////////////
  
  @Override
  public String toString(){
    String cards = "";
    for(int i = 0; i < deck.size(); i++){
      cards += deck.get(i).toString();
    }
    return cards;
  }
}
