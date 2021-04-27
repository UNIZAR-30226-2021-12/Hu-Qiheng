package com.unizar.unozar.core;

public class PlayerDeck{
  private Card deck[];
  private int numCards;
  
  public PlayerDeck(){
    deck = new Card[108];
    numCards = 0;
  }
  
  public boolean addCard(Card toAdd){
    if(numCards < 108){
      deck[numCards] = toAdd;
      numCards++;
      return true;
    }
    return false;
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
