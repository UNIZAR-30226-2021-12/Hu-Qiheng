package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateGameRequest{
  
  @JsonProperty
  private int maxPlayers;
  
  @JsonProperty
  private int numBots;
  
  @JsonProperty
  private String token;

  public int getMaxPlayers(){
    return maxPlayers;
  }

  public int getNumBots(){
    return numBots;
  }

  public String getToken(){
    return token;
  }
  
}
