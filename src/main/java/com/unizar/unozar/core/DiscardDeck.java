package com.unizar.unozar.core;

public class DiscardDeck{
  private Card deck[];
  private int numCards;
  
  public DiscardDeck(Card top){
    deck = new Card[108];
    deck[0] = top;
    numCards = 1;
  }
  
  public boolean playCard(Card toPlay){
    if((numCards < 108) && isPlayable(toPlay)){
      deck[numCards] = deck[numCards - 1];
      deck[numCards - 1] = toPlay;
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
}
