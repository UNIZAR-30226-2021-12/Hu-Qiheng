package com.unizar.unozar.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.unizar.unozar.core.DiscardDeck;
import com.unizar.unozar.core.DrawDeck;
import com.unizar.unozar.core.PlayerDeck;

@Entity
@Table(name = "GAME")
public class Game {
  
  private final int NOT_STARTED = -1;
  private final int NONE = 0;
  private final int SKIP = 1;
  private final int DRAW_TWO = 2;
  private final int DRAW_FOUR = 3;
  private final int FINISHED = 4;

  @Id
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
  
  @Column(name = "PLAYERS_DECKS")
  private PlayerDeck playersDecks[];
  
  @Column(name = "DRAW_DECK")
  private DrawDeck drawDeck;
  
  @Column(name = "DISCARD_DECK")
  private DiscardDeck discardDeck;
  
  @Column(name = "TURN")
  private int turn;
  
  @Column(name = "SPECIAL_EVENT")
  private int specialEvent;
  
  @Column(name = "NORMAL_FLOW")
  private boolean normalFlow;
  
  @Column(name = "IS_PAUSED")
  private boolean isPaused;
  
  public Game(){ // Don't even look at this
    isPrivate = true;
    maxPlayers = 4;
    numBots = 0;
    playersIds = new String[maxPlayers];
    playersDecks = new PlayerDeck[maxPlayers];
    for (int i = 0; i < maxPlayers; i++){
      playersIds[i] = "";
    }
    drawDeck = new DrawDeck();
    discardDeck = new DiscardDeck();
    turn = 0;
    specialEvent = NOT_STARTED;
    normalFlow = true;
    isPaused = false;
  }
  
  public Game(boolean isPrivate, int maxPlayers, int numBots, String player){
    this.isPrivate = isPrivate;
    this.maxPlayers = maxPlayers;
    this.numBots = numBots;
    playersIds = new String[maxPlayers];
    playersDecks = new PlayerDeck[maxPlayers];
    playersIds[0] = player;
    for (int i = 1 + numBots; i < maxPlayers; i++){
      playersIds[i] = "";
    }
    drawDeck = new DrawDeck();
    discardDeck = new DiscardDeck();
    turn = 0;
    specialEvent = NOT_STARTED;
    normalFlow = true;
    isPaused = false;
  }
  
  // Returns true if the player was added to the game, false otherwise
  public boolean addPlayer(String player){
    if(this.hasPlayer(player)) {
      return false;
    }
    for(int i = 1 + numBots; i < maxPlayers; i++){
      if(playersIds[i] == null){
        playersIds[i] = player;
        return true;
      }
    }
    return false;
  }
  
  // Returns true if there is place for someone else, false otherwise
  public boolean hasSpace(){
    int occupiedPlaces = 0;
    for(int i = 0; i < maxPlayers; i++){
     if(playersIds[i] != null){
       occupiedPlaces++;
     }
    }
    if((occupiedPlaces + numBots) < maxPlayers){
      return true;
    }
    return false;
  }
  
  // Returns true if the player was already added to the game, false otherwise
  private boolean hasPlayer(String player){
    for(int i = 0; i < maxPlayers; i++){
      if(playersIds[i] == player){
        return true;
      }
    }
    return false;
  }
  
  public boolean startGame(){
    //EMPTY
    return true;
  }

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
    return discardDeck.getTop().toString();
  }
  
  public int getTurn(){
    return turn;
  }
  
  public String[] getPlayersIds(){
    return playersIds;
  }
  
  public int[] getPlayersNumCards(){
    int playersNumCards[] = new int[maxPlayers];
    for (int i = 0; i < maxPlayers; i++){
      playersNumCards[i] = playersDecks[i].getNumCards();
    }
    return playersNumCards;
  }
  
  public String getPlayerCards(int playerNum){
    return playersDecks[playerNum].toString();
  }
}
