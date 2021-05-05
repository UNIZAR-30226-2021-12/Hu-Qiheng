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

import com.unizar.unozar.core.Card;
import com.unizar.unozar.core.exceptions.Como;
import com.unizar.unozar.core.exceptions.DeckFull;
import com.unizar.unozar.core.exceptions.DiscardDeckEmpty;
import com.unizar.unozar.core.exceptions.GameAlreadyStarted;
import com.unizar.unozar.core.exceptions.IncorrectTurn;
import com.unizar.unozar.core.exceptions.PlayerNotFound;
import com.unizar.unozar.core.exceptions.PlayerNotInGame;

@Entity
@Table(name = "GAME")
public class Game{
  
  private final int NOT_STARTED = -1;
  private final int NONE = 0;
  private final int DRAW_TWO = 1;
  private final int DRAW_FOUR = 2;
  private final int FINISHED = 3;
  
  public final String BOT = "BOT";
  public final String EMPTY = "EMPTY";
  
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
  private List<String> playerZeroDeck;
  
  @ElementCollection
  private List<String> playerOneDeck;
  
  @ElementCollection
  private List<String> playerTwoDeck;
  
  @ElementCollection
  private List<String> playerThreeDeck;
  
  @ElementCollection
  private List<String> drawDeck;
  
  @ElementCollection
  private List<String> discardDeck;
  
  @Column(name = "TURN")
  private int turn;
  
  @Column(name = "SPECIAL_EVENT")
  private int specialEvent;
  
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
    playerZeroDeck = new ArrayList<String>();
    playerOneDeck = new ArrayList<String>();
    playerTwoDeck = new ArrayList<String>();
    playerThreeDeck = new ArrayList<String>();
    drawDeck = new ArrayList<String>();
    discardDeck = new ArrayList<String>();
    turn = 0;
    specialEvent = NOT_STARTED;
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
    playerZeroDeck = new ArrayList<String>();
    playerOneDeck = new ArrayList<String>();
    playerTwoDeck = new ArrayList<String>();
    playerThreeDeck = new ArrayList<String>();
    drawDeck = new ArrayList<String>();
    discardDeck = new ArrayList<String>();
    turn = 0;
    specialEvent = NOT_STARTED;
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
  
  // Returns true if the player was quited from the game, false otherwise
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
    if(specialEvent != NOT_STARTED){
      throw new GameAlreadyStarted("You can not start a started game");
    }
    specialEvent = NONE;
    startDrawDeck();
    shuffleDrawDeck();
    for(int i = 0; i < maxPlayers; i++){
      for(int j = 0; j < 7; j++){
        playerZeroDeck.add(drawCard());
        playerOneDeck.add(drawCard());
        playerTwoDeck.add(drawCard());
        playerThreeDeck.add(drawCard());
      }
    }
  }

  private void startDrawDeck(){
    
    
  }
  
  public void addCard(List<String> deck, String toAdd){
    Card.checkCard(toAdd);
    if(deck.size() >= 108){
      throw new DeckFull("HOW?!?!?!?");
    }
    deck.add(toAdd);
  }

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

//  // Adds the special cards to the draw deck
//  private void addSpecials(){
//    String spec[] = Card.BASIC_SPEC;
//    String colors[] = Card.BASIC_COLORS;
//    // Colored special cards
//    for(int i = 0; i < colors.length; i++){
//      for(int j = 0; j < spec.length; j++){
//        addCard(Card.NONE + colors[i] + spec[j]);
//        addCard(Card.NONE + colors[i] + spec[j]);
//      }
//    }
//    // Black special cards
//    for(int i = 0; i < 4; i++){
//      addCard(Card.NONE + Card.BLACK + Card.DRAW_FOUR);
//      addCard(Card.NONE + Card.BLACK + Card.CHANGE_COLOR);
//    }
//  }
//
//  private void startDiscardDeck(){
//    boolean done = false;
//    while(!done){
//      String top = drawCard();
//      if(!Card.isBlack(top)){
//        discardDeck.startDeck(top);
//        done = true;
//      }else{
//        addCard(top);
//        shuffleDrawDeck();
//      }
//    }
//  }
  
  public boolean isGameStarted(){
    if(specialEvent != NOT_STARTED){
      return true;
    }
    return false;
  }

  public boolean isGamePaused(){
    return isPaused;
  }
  
  public boolean isGameFinished(){
    if(specialEvent == FINISHED){
      return true;
    }
    return false;
  }
  
//  public void playCard(String playerId, int cardToMove, 
//      boolean hasSaidUnozar, String colorSelected){
//    int playerNum = getPlayerNum(playerId);
//    if(playerNum == -1){
//      throw new PlayerNotInGame("The player is not in the game");
//    }
//    if(playerNum != turn){
//      throw new IncorrectTurn("It is not the player's turn");
//    }
//    if(playersDecks.get(playerNum).getNumCards() <= cardToMove){
//      throw new CardNotFound("The player does not have that many cards");
//    }
//  }
  
  public void drawCards(String playerId, int cardsToDraw, 
      boolean hasSaidUnozar){
    int playerNum = getPlayerNum(playerId);
    if(playerNum == -1){
      throw new PlayerNotInGame("The player is not in the game");
    }
    if(playerNum != turn){
      throw new IncorrectTurn("It is not the player's turn");
    }
    switch(specialEvent){
    case NOT_STARTED:
      notStartedDraw(playerId, cardsToDraw, hasSaidUnozar);
      break;
    case NONE:
      noneDraw(playerId, cardsToDraw, hasSaidUnozar);
      break;
    case DRAW_TWO:
      drawTwoDraw(playerId, cardsToDraw, hasSaidUnozar);
      break;
    case DRAW_FOUR:
      drawFourDraw(playerId, cardsToDraw, hasSaidUnozar);
      break;
    case FINISHED:
      finishedDraw(playerId, cardsToDraw, hasSaidUnozar);
      break;
    default:
      //throw new 
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
