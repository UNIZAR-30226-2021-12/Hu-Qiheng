package com.unizar.unozar.core;

public class Values{
  
  // Shop 
  public final static int AVATAR_PRICE = 250;
  public final static int BOARD_PRICE = 500;
  public final static int CARD_PRICE = 750;
  // Made so it can be changed the number of unlockables and their price
  public final static int A_SHOP[] = {0, 0, AVATAR_PRICE, AVATAR_PRICE,  
                                      AVATAR_PRICE, AVATAR_PRICE, AVATAR_PRICE, 
                                      AVATAR_PRICE};
  public final static int B_SHOP[] = {0, BOARD_PRICE, BOARD_PRICE};
  public final static int C_SHOP[] = {0, CARD_PRICE, CARD_PRICE};

  public final static int GIFTS[] = {10, 20, 30, 50, 100};
  
  // Time constants
  public final static int TURN_TIME = 60;
  public final static int TURN_TIME_IA = 10;
  public final static int PAUSE_EXTRA = 300;
  public static final int DAY_SECONDS = 86400;
  
  // Game constants
  public final static int NOT_STARTED = -1;
  public final static int PLAYING = 0;
  public final static int HAS_TO_DRAW_TWO = 1;
  public final static int HAS_TO_DRAW_FOUR = 2;
  public final static int FINISHED = 3;
  
  public final static String BOT = "BOT";
  public final static String EMPTY = "EMPTY";
  
  // Card constants
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

}
