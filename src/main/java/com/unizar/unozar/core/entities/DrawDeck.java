package com.unizar.unozar.core.entities;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.unizar.unozar.core.exceptions.DeckFull;

@Entity
@Table(name = "DRAW_DECK")
public class DrawDeck{
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  
  @Column
  private Card deck[];
  
  @Column
  private int numCards;
  
  public DrawDeck(){
    deck = new Card[108];
    numCards = 0;
    this.addNumbers();
    this.addSpecials();
  }
  
  public void addCard(Card toAdd){
    if(numCards < 108){
      deck[numCards] = toAdd;
      numCards++;
    }else{
      throw new DeckFull("HOW?!?!?!?");
    }
  }
  
  private boolean addCard(int number, int color, int specialFunct){
    if(numCards < 108){
      deck[numCards] = new Card(number, color, specialFunct);
      numCards++;
      return true;
    }
    return false;
  }
  
  private int addNumber(int number, int color){
    if(addCard(number, color, Card.NONE)){
      return 1;
    }
    return 0;
  }

  private int addSpecial(int color, int specialFunct){
    if(addCard(Card.NONE, color, specialFunct)){
      return 1;
    }
    return 0;
  }
  
  private boolean addNumbers(){
    int colors[] = Card.BASIC_COLORS;
    int cardsAdded = 0;
    for(int i = 0; i < colors.length; i++){
      cardsAdded += addNumber(0, colors[i]);
      for(int j = 1; j <= 9; j++){
        cardsAdded += addNumber(j, colors[i]);
        cardsAdded += addNumber(j, colors[i]);
      }
    }
    if(cardsAdded == 76){
      return true;
    }
    return false;
  }
  
  private boolean addSpecials(){
    int specialFunct[] = Card.BASIC_SPECIAL_FUNCT;
    int colors[] = Card.BASIC_COLORS;
    int cardsAdded = 0;

    for(int i = 0; i < colors.length; i++){
      for(int j = 0; j < specialFunct.length; j++){
        cardsAdded += addSpecial(colors[i], specialFunct[j]);
        cardsAdded += addSpecial(colors[i], specialFunct[j]);
      }
    }
    for(int i = 0; i < 4; i++){
      cardsAdded += addSpecial(Card.BLACK, Card.DRAW_FOUR);
      cardsAdded += addSpecial(Card.BLACK, Card.CHANGE_COLOR);
    }
    if(cardsAdded == 32){
      return true;
    }
    return false;
  }
  
  public void shuffle(){
    int index;
    Card temp;
    Random random = new Random();
    for(int i = numCards - 1; i > 0; i--){
      index = random.nextInt(i + 1);
      temp = deck[index];
      deck[index] = deck[i];
      deck[i] = temp;
    }
  }
  
  public Card drawCard(){
    numCards--;
    return deck[numCards];
  }
  
  public int replenishDeck(Card x[]){
    if((numCards == 0) && (numCards + x.length <= 108)){
      for(int i = 0; i < x.length; i++){
        deck[i] = x[i];
      }
      numCards = x.length;
      return numCards;
    }
    return 0;
  }
}
