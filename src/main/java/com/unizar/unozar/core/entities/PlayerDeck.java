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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.unizar.unozar.core.exceptions.DeckFull;

@Entity
@Table(name = "PLAYER_DECK")
public class PlayerDeck{
  
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @OneToMany(mappedBy = "playerDeck", cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Card> deck;
  
  @ManyToOne()
  @JoinColumn(name = "GAME_ID")
  private Game game;
  
  public PlayerDeck(){
    deck = new ArrayList<Card>();
  }
  
  public void addCard(Card toAdd){
    if(deck.size() < 108){
      deck.add(toAdd);
    }
    throw new DeckFull("HOW?!?");
  }
  
  public Card select(int cardNum){
    if(cardNum < deck.size() - 1){
      Card selected = deck.get(cardNum);
      deck.remove(cardNum);
      return selected;
    }
    return null;
  }
  
  public int getNumCards(){
    return deck.size();
  }
  
  @Override
  public String toString(){
    String cards = "";
    for(int i = 0; i < deck.size(); i++){
      cards += deck.get(i).toString();
    }
    return cards;
  }
}
