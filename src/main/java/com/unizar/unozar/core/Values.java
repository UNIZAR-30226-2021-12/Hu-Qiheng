package com.unizar.unozar.core;

public class Values{
  
  public final static int SHOP[] = {100, 200, 300, 600, 1200, 1300};
  
  public final static int GIFTS[] = {10, 20, 30, 50, 100};
  
  public final static int TURN_TIME = 60;
  public final static int TURN_TIME_IA = 10;
  public final static int PAUSE_EXTRA = 300;
  public static final int DAY_SECONDS = 86400;
  
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
