package com.unizar.unozar.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.unizar.unozar.core.PlayerDeck;

@Entity
@Table(name = "GAME")
public class Game {
  
  private final int NOT_STARTED = -1;
  private final int NONE = 0;
  private final int SKIP = 1;
  private final int STEAL_TWO = 2;
  private final int STEAL_FOUR = 4;
  
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
  private String players[];
  
  @Column(name = "PLAYERS_DECKS")
  private PlayerDeck playersDecks[];
  
  @Column(name = "SPECIAL_EVENT")
  private int specialEvent;
  
  @Column(name = "NORMAL_FLOW")
  private boolean normalFlow;
  
  public Game(){
    isPrivate = true;
    maxPlayers = 4;
    numBots = 0;
    players = new String[maxPlayers];
    playersDecks = new PlayerDeck[maxPlayers];
    players[0] = null;
    for (int i = 1 + numBots; i < maxPlayers; i++){
      players[i] = null;
    }
  }
  
  public Game(boolean isPrivate, int maxPlayers, int numBots, String player){
    this.isPrivate = isPrivate;
    this.maxPlayers = maxPlayers;
    this.numBots = numBots;
    players = new String[maxPlayers];
    playersDecks = new PlayerDeck[maxPlayers];
    players[0] = player;
    for (int i = 1 + numBots; i < maxPlayers; i++){
      players[i] = null;
    }
  }
  
  // Returns true if the player was added to the game, false otherwise
  public boolean addPlayer(String player){
    if(this.hasPlayer(player)) {
      return false;
    }
    for(int i = 1 + numBots; i < maxPlayers; i++){
      if(players[i] == null){
        players[i] = player;
        return true;
      }
    }
    return false;
  }
  
  // Returns true if there is place for someone else, false otherwise
  public boolean hasSpace(){
    int occupiedPlaces = 0;
    for(int i = 0; i < maxPlayers; i++){
     if(players[i] != null){
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
      if(players[i] == player){
        return true;
      }
    }
    return false;
  }
  
  public boolean startGame(){
    //EMPTY
    return true;
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
  
  public String getPlayers(){
    String result = "";
    for(int i = 0; i < players.length - 1; i ++){
      result += players[i]+",";
    }
    result += players[players.length - 1];
    return result;
  }
}
