package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JoinPrivateGameRequest{
  
  @JsonProperty
  private String gameId;
  
  @JsonProperty
  private String token;

  public String getGameId(){
    return gameId;
  }

  public String getToken(){
    return token;
  }
  
}
