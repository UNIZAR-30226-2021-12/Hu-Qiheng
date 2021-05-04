package com.unizar.unozar.core;

import com.unizar.unozar.core.exceptions.IncorrectCard;

public class Card{
  
  // Special value
  public final static String NONE = "X";

  // Colors
  public final static String BLACK = "X";
  public final static String RED = "R";
  public final static String YELLOW = "Y";
  public final static String GREEN = "G";
  public final static String BLUE = "B";

  public final static String BASIC_COLORS[] = {RED, YELLOW, GREEN, BLUE};

  // Special functions
  public final static String REVERSE = "R";
  public final static String SKIP = "S";
  public final static String DRAW_TWO = "2";
  public final static String DRAW_FOUR = "4";
  public final static String CHANGE_COLOR = "C";

  public final static String BASIC_SPEC[] = {REVERSE, SKIP, DRAW_TWO};
  public final static String SPECIAL_SPEC[] = {DRAW_FOUR, CHANGE_COLOR};

  // Check if the given String is a correct card, throws IncorrectCard otherwise
  public static void checkCard(String card){
    // Check incorrect length
    if(card.length() != 3){
      throw new IncorrectCard("That's not a card");
    }
    char number = card.charAt(0);
    char color = card.charAt(1);
    char spec = card.charAt(2);
    // Check incorrect number
    if((number != 'X') && (!Character.isDigit(number))){
      throw new IncorrectCard("That's not a card");
    }
    // Check incorrect color
    if((color != 'R') && (color != 'Y') && (color != 'G') && (color != 'B') && 
        (color != 'X')){
      throw new IncorrectCard("That's not a card");
    }
    // Check incorrect spec
    if((spec != 'R') && (spec != 'S') && (spec != '2') && (spec != '4') &&
        (spec != 'C') && (spec != 'X')){
      throw new IncorrectCard("That's not a card");
    }
  }
  
  // Check if given cards are related by number, color or spec
  public static boolean areCardsRelated(String cardOne, String cardTwo){
    // Check if same number
    char numberOne = cardOne.charAt(0);
    char numberTwo = cardTwo.charAt(0);
    if(Character.isDigit(numberOne) && (numberOne == numberTwo)){
      return true;
    }
    // Check if same color
    char colorOne = cardOne.charAt(1);
    char colorTwo = cardTwo.charAt(1);
    if((colorOne != 'X') && (colorOne == colorTwo)){
      return true;
    }
    // Check if same spec
    char specOne = cardOne.charAt(2);
    char specTwo = cardTwo.charAt(2);
    if((specOne != 'X') && (specOne == specTwo)){
      return true;
    }
    return false;
  }
  
  public static boolean isBlack(String card){
    return Character.toString(card.charAt(1)).equals(BLACK);
  }
}
