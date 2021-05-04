package com.unizar.unozar.core.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.unizar.unozar.core.Card;
import com.unizar.unozar.core.exceptions.Como;
import com.unizar.unozar.core.exceptions.DeckFull;
import com.unizar.unozar.core.exceptions.DrawDeckNotEmpty;

@Entity
@Table(name = "DRAW_DECK")
public class DrawDeck{
  
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  
  @ElementCollection
  private List<String> deck;
  
  public DrawDeck(){
    deck = new ArrayList<String>();
    this.addNumbers();
    this.addSpecials();
  }
  
  public void addCard(String toAdd){
    Card.checkCard(toAdd);
    if(deck.size() >= 108){
      throw new DeckFull("HOW?!?!?!?");
    }
    deck.add(toAdd);
  }
  
  // Adds the numeric cards to the deck
  private void addNumbers(){
    // Zeros go just once
    addCard("0RX");
    addCard("0YX");
    addCard("0GX");
    addCard("0BX");
    // All the others go twice
    for(int i = 1; i < 10; i++){
      char c = (char)(i + '0');
      addCard(c+"RX");
      addCard(c+"RX");
      addCard(c+"YX");
      addCard(c+"YX");
      addCard(c+"GX");
      addCard(c+"GX");
      addCard(c+"BX");
      addCard(c+"BX");
    }
  }
  
  // Adds the special cards to the deck
  private void addSpecials(){
    String spec[] = Card.BASIC_SPEC;
    String colors[] = Card.BASIC_COLORS;
    // Colored special cards
    for(int i = 0; i < colors.length; i++){
      for(int j = 0; j < spec.length; j++){
        addCard(Card.NONE + colors[i] + spec[j]);
        addCard(Card.NONE + colors[i] + spec[j]);
      }
    }
    // Black special cards
    for(int i = 0; i < 4; i++){
      addCard(Card.NONE + Card.BLACK + Card.DRAW_FOUR);
      addCard(Card.NONE + Card.BLACK + Card.CHANGE_COLOR);
    }
  }
  
  public void shuffle(){
    int index;
    String temp;
    Random random = new Random();
    for(int i = deck.size() - 1; i > 0; i--){
      index = random.nextInt(i + 1);
      temp = deck.get(index);
      deck.add(index, deck.get(i));
      deck.add(i, temp);
    }
  }
  
  public String drawCard(){
    String toDraw = deck.get(deck.size() - 1);
    deck.remove(deck.size() - 1);
    return toDraw;
  }
  
  public void replenishDeck(String x[]){
    if(deck.size()!= 0){
      throw new DrawDeckNotEmpty("The draw deck is not empty");
    }
    if(x.length > 108){
      throw new Como("WHAT?! no quiero ni preguntar");
    }
    for(int i = 0; i < x.length; i++){
      addCard(x[i]);
    }
  }
  
  /////////////////////////
  // Getters and Setters //
  /////////////////////////
  
}
