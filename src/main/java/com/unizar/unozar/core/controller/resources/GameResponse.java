package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unizar.unozar.core.entities.Game;

public class GameResponse{

  @JsonProperty
  public String gameId;
  
  @JsonProperty
  public int maxPlayers;
  
  @JsonProperty
  public String topDiscard;
  
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
  
  @JsonProperty
  public String token;

  public GameResponse(Game game, int playerNum, String newToken){
    gameId = game.getId();
    maxPlayers = game.getMaxPlayers();
    topDiscard = game.getTopDiscardString();
    turn = game.getTurn();
    playersIds = new String[maxPlayers];
    playersIds = game.getPlayersIds();
    playersNumCards = new int[maxPlayers];
    for (int i = 0; i < maxPlayers; i++){
      playersNumCards[i] = game.getPlayerNumCards(i);
    }
    gameStarted = game.isGameStarted();
    gamePaused = game.isGamePaused();
    gameFinished = game.isGameFinished();
    playerCards = game.getPlayerCards(playerNum);
    token = newToken;
  }
  
}
