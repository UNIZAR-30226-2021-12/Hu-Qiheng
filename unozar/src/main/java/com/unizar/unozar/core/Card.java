package com.unizar.unozar.core;




public class Card {
  
  // Special value
  public final static int NONE = -1;
  
  // Colors
  public final static int BLACK = 0;
  public final static int RED = 1;
  public final static int YELLOW = 2;
  public final static int GREEN = 3;
  public final static int BLUE = 4;
  
  public final static int BASIC_COLORS[] = {RED, YELLOW, GREEN, BLUE};
  
  // Special functions
  public final static int REVERSE = 0;
  public final static int SKIP = 1;
  public final static int STEAL_TWO = 2;
  public final static int STEAL_FOUR = 3;
  public final static int CHANGE_COLOR = 4;
  
  public final static int BASIC_SPECIAL_FUNCT[] = {REVERSE, SKIP, STEAL_TWO};
  
  
  private int num;
  private int color;
  private int specialFunct; // Special function
  
  public Card(int num, int color, int specialFunct){
    this.num = num;
    this.color = color;
    this.specialFunct = specialFunct;
  }
  
  public boolean isRelatedWith(Card otherCard){
    if((isSameColor(otherCard)) || (isSameNum(otherCard)) || 
        (isSameFunct(otherCard))){
      return true;
    }
    return false;
  }
  
  public boolean isSameNum(Card otherCard){
    if((num != NONE) && (num == otherCard.getNum())){
      return true;
    }
    return false;
  }
  

  public boolean isSameColor(Card otherCard){
    if(color == otherCard.getColor()){
      return true;
    }
    return false;
  }

  public boolean isSameFunct(Card otherCard){
    if((specialFunct != NONE) && (specialFunct == otherCard.getSpecialFunct())){
      return true;
    }
    return false;
  }
  
  /////////////////////////
  // Getters and Setters //
  /////////////////////////
  
  public int getNum(){
    return num;
  }
  
  public int getColor(){
    return color;
  }
  
  public int getSpecialFunct(){
    return specialFunct;
  }
  
  ///////////////
  // TO_STRING //
  ///////////////
  
  @Override
  public String toString(){
    return "N:" + num + "-C:" + color + "-F:" + specialFunct;
  }
}
