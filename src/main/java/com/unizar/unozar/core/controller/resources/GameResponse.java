package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unizar.unozar.core.entities.Game;

public class GameResponse{
  
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
  public boolean gamePaused;
  
  @JsonProperty
  public String token;

  public GameResponse(Game game, int playerNum, String newToken){
    maxPlayers = game.getMaxPlayers();
    turn = game.getTurn();
    playersIds = new String[maxPlayers];
    playersIds = game.getPlayersIds();
    playersNumCards =  game.getPlayersDecksNumCards();
    topDiscard = game.getTopDiscardString();  
    gamePaused = game.isGamePaused();
    playerCards = game.getPlayerCards(playerNum);
    token = newToken;
  }
  
}
