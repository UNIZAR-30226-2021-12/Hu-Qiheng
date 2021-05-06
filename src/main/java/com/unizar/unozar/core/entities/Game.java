package com.unizar.unozar.core.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.unizar.unozar.core.exceptions.CardNotFound;
import com.unizar.unozar.core.exceptions.Como;
import com.unizar.unozar.core.exceptions.DeckFull;
import com.unizar.unozar.core.exceptions.DiscardDeckEmpty;
import com.unizar.unozar.core.exceptions.GameAlreadyStarted;
import com.unizar.unozar.core.exceptions.IncorrectAction;
import com.unizar.unozar.core.exceptions.IncorrectCard;
import com.unizar.unozar.core.exceptions.IncorrectTurn;
import com.unizar.unozar.core.exceptions.PlayerNotFound;
import com.unizar.unozar.core.exceptions.PlayerNotInGame;

@Entity
@Table(name = "GAME")
public class Game{
  
  private final static int NOT_STARTED = -1;
  private final static int PLAYING = 0;
  private final static int HAS_TO_DRAW_TWO = 1;
  private final static int HAS_TO_DRAW_FOUR = 2;
  private final static int FINISHED = 3;
  
  public final static String BOT = "BOT";
  public final static String EMPTY = "EMPTY";
  
  // Card constants
  // Special value
  private final static String NONE = "X";

  // Colors
  private final static String BLACK = "X";
  private final static String RED = "R";
  private final static String YELLOW = "Y";
  private final static String GREEN = "G";
  private final static String BLUE = "B";

  private final static String BASIC_COLORS[] = {RED, YELLOW, GREEN, BLUE};

  // Special functions
  private final static String REVERSE = "R";
  private final static String SKIP = "S";
  private final static String DRAW_TWO = "2";
  private final static String DRAW_FOUR = "4";
  private final static String CHANGE_COLOR = "C";

  private final static String BASIC_SPEC[] = {REVERSE, SKIP, DRAW_TWO};
  
  @Id
  @Column(name = "ID")
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;
  
  @Column(name = "IS_PRIVATE")
  private boolean isPrivate;
  
  @Column(name = "MAX_PLAYERS")
  private int maxPlayers;
  
  @Column(name = "NUM_BOTS")
  private int numBots;
  
  @Column(name = "PLAYERS")
  private String playersIds[];
  
  @ElementCollection
  private List<String> playerZeroDeck = new ArrayList<String>();
  
  @ElementCollection
  private List<String> playerOneDeck = new ArrayList<String>();
  
  @ElementCollection
  private List<String> playerTwoDeck = new ArrayList<String>();
  
  @ElementCollection
  private List<String> playerThreeDeck = new ArrayList<String>();
  
  @ElementCollection
  private List<String> drawDeck = new ArrayList<String>();
  
  @ElementCollection
  private List<String> discardDeck = new ArrayList<String>();
  
  @Column(name = "TURN")
  private int turn;
  
  @Column(name = "STATUS")
  private int status;
  
  @Column(name = "NORMAL_FLOW")
  private boolean normalFlow;
  
  @Column(name = "IS_PAUSED")
  private boolean isPaused;
  
  @Column(name = "WINNER")
  private int winner;
  
  @Column(name = "END_CHECKED")
  private boolean endChecked[];
  
  public Game(){ // Don't even dare to look at this
    isPrivate = true;
    maxPlayers = 4;
    numBots = 0;
    playersIds = new String[maxPlayers];
    endChecked = new boolean[maxPlayers];
    for (int i = 0; i < maxPlayers; i++){
      playersIds[i] = "";
      endChecked[i] = false;
    }
    turn = 0;
    status = NOT_STARTED;
    normalFlow = true;
    isPaused = false;
    winner = 0;
  }
  
  public Game(boolean isPrivate, int maxPlayers, int numBots, String player){
    this.isPrivate = isPrivate;
    this.maxPlayers = maxPlayers;
    this.numBots = numBots;
    playersIds = new String[maxPlayers];
    endChecked = new boolean[maxPlayers];
    playersIds[0] = player;
    endChecked[0] = false;
    for (int i = 1; i < 1 + numBots; i++){
      playersIds[i] = BOT;
      endChecked[i] = false;
    }
    for (int i = 1 + numBots; i < maxPlayers; i++){
      playersIds[i] = EMPTY;
      endChecked[i] = false;
    }
    turn = 0;
    status = NOT_STARTED;
    normalFlow = true;
    isPaused = false;
    winner = 0;
  }
  
  // Returns true if the player was added to the game, false otherwise
  public boolean addPlayer(String playerId){
    if(this.hasPlayer(playerId)){
      return false;
    }
    for(int i = 1 + numBots; i < maxPlayers; i++){
      if(playersIds[i].equals(EMPTY)){
        playersIds[i] = playerId;
        return true;
      }
    }
    return false;
  }
  
  // Returns true if the player was quit from the game, false otherwise
  public boolean quitPlayer(String playerId){
    if(!this.hasPlayer(playerId)){
      return false;
    }
    for(int i = 0; i < maxPlayers; i++){
      if(playersIds[i].equals(playerId)){
        playersIds[i] = EMPTY;
        return true;
      }
    }
    return false;
  }
  
  public boolean toOwner(String newOwnerId){
    if(!this.hasPlayer(newOwnerId)){
      return false;
    }
    for(int i = 0; i < maxPlayers; i++){
      if(playersIds[i].equals(newOwnerId)){
        if(!playersIds[0].equals(EMPTY)){
          throw new Como("AWHDIASHD");
        }
        playersIds[0] = playersIds[i];
        playersIds[i] = EMPTY;
        return true;
      }
    }
    return false;
  }
  
  // Returns true if there is place for someone else, false otherwise
  public boolean hasSpace(){
    for(int i = 1 + numBots; i < maxPlayers; i++){
      if(playersIds[i].equals(EMPTY)){
        return true;
      }
    }
    return false;
  }
  
  // Returns true if the player is currently in the game, false otherwise
  public boolean hasPlayer(String player){
    for(int i = 0; i < maxPlayers; i++){
      if(playersIds[i].equals(player)){
        return true;
      }
    }
    return false;
  }
  
  public boolean hasAnyPlayer(){
    for(int i = 0; i < maxPlayers; i++){
      if(!playersIds[i].equals(BOT) && !playersIds[i].equals(EMPTY)){
        return true;
      }
    }
    return false;
  }
  
  public void startGame(){
    if(status != NOT_STARTED){
      throw new GameAlreadyStarted("You can not start a started game");
    }
    status = PLAYING;
    addNumbers();
    addSpecials();
    shuffleDrawDeck();
    for(int j = 0; j < 7; j++){
      switch (maxPlayers){
      case 4:
        playerThreeDeck.add(drawCard());        
      case 3: 
        playerTwoDeck.add(drawCard());
      case 2:
        playerOneDeck.add(drawCard());
        playerZeroDeck.add(drawCard());     
      }
    }
    startDiscardDeck();
  }
  
  public void addCard(List<String> deck, String toAdd){
    checkCard(toAdd);
    if(deck.size() >= 108){
      throw new DeckFull("HOW?!?!?!?");
    }
    deck.add(toAdd);
  }

  private void startDiscardDeck(){
    boolean done = false;
    while(!done){
      String top = drawCard();
      if(!isBlack(top)){
        discardDeck.add(top);
        done = true;
      }else{
        drawDeck.add(top);
        shuffleDrawDeck();
      }
    }
  }
  
  public boolean isGameStarted(){
    if(status != NOT_STARTED){
      return true;
    }
    return false;
  }

  public boolean isGamePaused(){
    return isPaused;
  }
  
  public boolean isGameFinished(){
    if(status == FINISHED){
      return true;
    }
    return false;
  }
  
  public void playCard(String playerId, int cardToMove, 
      boolean hasSaidUnozar, String colorSelected){
    int playerNum = getPlayerNum(playerId);
    if(playerNum == -1){
      throw new PlayerNotInGame("The player is not in the game");
    }
    if(playerNum != turn){
      throw new IncorrectTurn("It is not the player's turn");
    }
    if(status != PLAYING){
      throw new IncorrectAction("Now is not the moment to play a card");
    }
    if(getPlayerDeckNumCards(getPlayerNum(playerId)) <= cardToMove){
      throw new CardNotFound("The player does not have that many cards");
    }
    String cardToPlay = getDeckByPlayerNum(playerNum).get(cardToMove);
    if(isCardPlayable(cardToPlay)){
      if(isBlack(cardToPlay)){
        cardToPlay = "X" + colorSelected + cardToPlay.charAt(2);
        checkCard(cardToPlay);
      }
      discardDeck.add(cardToPlay);
      getDeckByPlayerNum(playerNum).remove(cardToMove);
      if(getDeckByPlayerNum(playerNum).size() == 0){
        winner = playerNum;
        status = FINISHED;
      }else{
        checkUnozar(playerNum, hasSaidUnozar);
        updateGameStatus(true);
      }
    }else{
      throw new IncorrectCard("That card cannot be played now");
    }
  }
  
  private void checkUnozar(int playerNum, boolean hasSaidUnozar){
    if(getDeckByPlayerNum(playerNum).size() == 1){
      if(!hasSaidUnozar){
        getDeckByPlayerNum(playerNum).add(drawCard());
      }
    }else if(hasSaidUnozar){
      getDeckByPlayerNum(playerNum).add(drawCard());
    }
  }

  private void updateGameStatus(boolean cardPlayedInThisTurn){
    if(cardPlayedInThisTurn){
      String top = discardDeck.get(discardDeck.size() - 1);
      switch(Character.toString(top.charAt(2))){
      case REVERSE:
        normalFlow = !normalFlow;
        if(normalFlow){
          turn = (turn + 1) % (maxPlayers - 1);
        }else{
          advanceReverseTurn();
        }
        status = PLAYING;
        break;
      case SKIP:
        if(normalFlow){
          turn = (turn + 1) % (maxPlayers - 1);
          turn = (turn + 1) % (maxPlayers - 1);
        }else{
          advanceReverseTurn();
          advanceReverseTurn();
        }
        status = PLAYING;
        break;
      case DRAW_TWO:
        status = HAS_TO_DRAW_TWO;
        break;
      case DRAW_FOUR:
        status = HAS_TO_DRAW_FOUR;
        break;
      }
    }else{
      status = PLAYING;
    }
  }

  private void advanceReverseTurn(){
    turn--;
    if(turn == -1){
      turn = maxPlayers - 1;
    }
  }

  public void drawCards(String playerId, int cardsToDraw, 
      boolean hasSaidUnozar){
    int playerNum = getPlayerNum(playerId);
    if(playerNum == -1){
      throw new PlayerNotInGame("The player is not in the game");
    }
    if(playerNum != turn){
      throw new IncorrectTurn("It is not the player's turn");
    }
    switch(status){
    case NOT_STARTED:
      notStartedDraw(playerId, cardsToDraw, hasSaidUnozar);
      break;
    case PLAYING:
      noneDraw(playerId, cardsToDraw, hasSaidUnozar);
      break;
    case HAS_TO_DRAW_TWO:
      drawTwoDraw(playerId, cardsToDraw, hasSaidUnozar);
      break;
    case HAS_TO_DRAW_FOUR:
      drawFourDraw(playerId, cardsToDraw, hasSaidUnozar);
      break;
    case FINISHED:
      finishedDraw(playerId, cardsToDraw, hasSaidUnozar);
      break;
    default:
      //throw new 
    }
  }
  
  public void shuffleDrawDeck(){
    int index;
    String temp;
    Random random = new Random();
    for(int i = drawDeck.size() - 1; i > 0; i--){
      index = random.nextInt(i + 1);
      temp = drawDeck.get(index);
      drawDeck.add(index, drawDeck.get(i));
      drawDeck.add(i, temp);
    }
  }
  
  public String drawCard(){
    String toDraw = drawDeck.get(drawDeck.size() - 1);
    drawDeck.remove(drawDeck.size() - 1);
    return toDraw;
  }
  
  /////////////////////
  // Private methods //
  /////////////////////
  
  // Adds the numeric cards to the draw deck
  private void addNumbers(){
    // Zeros go just once
    addCard(drawDeck, "0RX");
    addCard(drawDeck, "0YX");
    addCard(drawDeck, "0GX");
    addCard(drawDeck, "0BX");
    // All the others go twice
    for(int i = 1; i < 10; i++){
      char c = (char)(i + '0');
      addCard(drawDeck, c+"RX");
      addCard(drawDeck, c+"RX");
      addCard(drawDeck, c+"YX");
      addCard(drawDeck, c+"YX");
      addCard(drawDeck, c+"GX");
      addCard(drawDeck, c+"GX");
      addCard(drawDeck, c+"BX");
      addCard(drawDeck, c+"BX");
    }
  }

  // Adds the special cards to the draw deck
  private void addSpecials(){
    String spec[] = BASIC_SPEC;
    String colors[] = BASIC_COLORS;
    // Colored special cards
    for(int i = 0; i < colors.length; i++){
      for(int j = 0; j < spec.length; j++){
        addCard(drawDeck, NONE + colors[i] + spec[j]);
        addCard(drawDeck, NONE + colors[i] + spec[j]);
      }
    }
    // Black special cards
    for(int i = 0; i < 4; i++){
      addCard(drawDeck, NONE + BLACK + DRAW_FOUR);
      addCard(drawDeck, NONE + BLACK + CHANGE_COLOR);
    }
  }
  
  private void finishedDraw(String playerId, int cardsToDraw, 
      boolean hasSaidUnozar){
    // TODO Auto-generated method stub
    
  }

  private void drawFourDraw(String playerId, int cardsToDraw, 
      boolean hasSaidUnozar){
    // TODO Auto-generated method stub
    
  }

  private void drawTwoDraw(String playerId, int cardsToDraw, 
      boolean hasSaidUnozar){
    // TODO Auto-generated method stub
    
  }

  private void noneDraw(String playerId, int cardsToDraw, 
      boolean hasSaidUnozar){
    // TODO Auto-generated method stub
    
  }

  private void notStartedDraw(String playerId, int cardsToDraw, 
      boolean hasSaidUnozar){
    // TODO Auto-generated method stub
  }
  
  //////////////////
  // Card methods //
  //////////////////
  // Check if the given String is a correct card, throws IncorrectCard otherwise
  private void checkCard(String card){
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
  
  // Check if given card can be played
  private boolean isCardPlayable(String card){
    if(discardDeck.size() == 0){
      throw new DiscardDeckEmpty("Cannot play cards with empty discard deck");
    }
    String top = discardDeck.get(discardDeck.size() - 1);
    // Check if same number
    char numberOne = top.charAt(0);
    char numberTwo = card.charAt(0);
    if(Character.isDigit(numberOne) && (numberOne == numberTwo)){
      return true;
    }
    // Check if same color or card to play is black
    char colorOne = top.charAt(1);
    char colorTwo = card.charAt(1);
    if(((colorOne != 'X') && (colorOne == colorTwo)) || isBlack(card)){
      return true;
    }
    // Check if same spec
    char specOne = top.charAt(2);
    char specTwo = card.charAt(2);
    if((specOne != 'X') && (specOne == specTwo)){
      return true;
    }
    return false;
  }
  
  private boolean isBlack(String card){
    return Character.toString(card.charAt(1)).equals(BLACK);
  }
  
  /////////////////////////
  // Getters and Setters //
  /////////////////////////

  public String getId(){
    return id;
  }
  
  public int getMaxPlayers(){
    return maxPlayers;
  }
  
  public int getNumBots(){
    return numBots;
  }
  
  public int getPlayerNum(String playerId){
    for(int i = 0; i < maxPlayers; i++){
      if(playersIds[i].equals(playerId)){
        return i;
      }
    }
    return -1;
  }
  
  public String getTopDiscardString(){
    if(getDiscardDeckNumCards() == 0){
      throw new DiscardDeckEmpty("The discard deck is empty");
    }
    return discardDeck.get(discardDeck.size() - 1);
  }
  
  public int getTurn(){
    return turn;
  }
  
  public String[] getPlayersIds(){
    return playersIds;
  }
  
  public String getOwner(){
    return playersIds[0];
  }
  
  public int getPlayerDeckNumCards(int playerNum){
    return getDeckByPlayerNum(playerNum).size();
  }
  
  public int getDiscardDeckNumCards(){
    return discardDeck.size();
  }
  
  private List<String> getDeckByPlayerNum(int playerNum){
    if(playerNum > maxPlayers - 1){
      throw new PlayerNotFound("There is not that many players");
    }
    switch(playerNum){
    case 0:
      return playerZeroDeck;
    case 1:
      return playerOneDeck;
    case 2:
      return playerTwoDeck;
    case 3:
      return playerThreeDeck;
    default:
      throw new PlayerNotFound("Invalid value of playerNum");
    }
  }

  public int[] getPlayersDecksNumCards(){
    int playersNumCards[] = new int[maxPlayers];
    for (int i = 0; i < maxPlayers; i++){
      playersNumCards[i] = getPlayerDeckNumCards(i);
    }
    return playersNumCards;
  }
  
  public String[] getPlayerCards(int playerNum){
    return getDeckByPlayerNum(playerNum).toArray(new String[0]);
  }

}
