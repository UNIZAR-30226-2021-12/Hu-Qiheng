package com.unizar.unozar.core;

import java.util.Random;

public class StealDeck{
  private Card deck[];
  private int numCards;
  
  public StealDeck(){
    deck = new Card[108];
    numCards = 0;
    this.addNumbers();
    this.addSpecials();
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
      cardsAdded += addSpecial(Card.BLACK, Card.STEAL_FOUR);
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
  
  public Card stealOne(){
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
