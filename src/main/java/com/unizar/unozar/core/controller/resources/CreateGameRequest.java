package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateGameRequest{
  
  @JsonProperty
  private boolean isPrivate;
  
  @JsonProperty
  private int maxPlayers;
  
  @JsonProperty
  private int numBots;
  
  // Only for public games
  @JsonProperty
  private int bet;
  
  @JsonProperty
  private String token;
  
  public int getBet(){
    return bet;
  }
  
  public boolean getIsPrivate(){
    return isPrivate;
  }

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
