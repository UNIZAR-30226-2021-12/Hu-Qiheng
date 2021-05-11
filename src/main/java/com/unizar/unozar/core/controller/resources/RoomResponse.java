package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unizar.unozar.core.entities.Game;

public class RoomResponse{
  
  @JsonProperty
  public String gameId;
  
  @JsonProperty
  public int maxPlayers;
  
  @JsonProperty
  public boolean gameStarted;
  
  @JsonProperty
  public String[] playersIds;
  
  @JsonProperty
  public String token;
  
  public RoomResponse(Game toRead, String token){
    gameId = toRead.getId();
    maxPlayers = toRead.getMaxPlayers();
    gameStarted = toRead.isGameStarted();
    playersIds = new String[maxPlayers];
    playersIds = toRead.getPlayersIds();
    this.token = token;
  }

}
