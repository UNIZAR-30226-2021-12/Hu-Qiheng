package com.unizar.unozar.core.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unizar.unozar.core.entities.Game;

public class GameDTO{

  @JsonProperty
  public String topDiscardPile;

  @JsonProperty
  public String[] playerCards;
  
  @JsonProperty
  public int turn;
  
  @JsonProperty
  public String[] playersIds;
  
  @JsonProperty
  public int[] playersNumCards;

  @JsonProperty
  public boolean gameStarted;
  
  @JsonProperty
  public boolean gamePaused;
  
  @JsonProperty
  public boolean gameFinished;

  public GameDTO(Game game, int playerNum){
    
  }
  
}
