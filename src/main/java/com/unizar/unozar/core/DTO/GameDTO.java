package com.unizar.unozar.core.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unizar.unozar.core.entities.Game;

public class GameDTO{

  @JsonProperty
  public int maxPlayers;
  
  @JsonProperty
  public String topDiscard;
  
  @JsonProperty
  public String playerCards;
  
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
    maxPlayers = game.getMaxPlayers();
    topDiscard = game.getTopDiscardString();
    turn = game.getTurn();
    playersIds = new String[maxPlayers];
    playersIds = game.getPlayersIds();
    playersNumCards = new int[maxPlayers];
    playersNumCards = game.getPlayersNumCards();
    gameStarted = game.isGameStarted();
    gamePaused = game.isGamePaused();
    gameFinished = game.isGameFinished();
    playerCards = game.getPlayerCards(playerNum);
  }
  
}
