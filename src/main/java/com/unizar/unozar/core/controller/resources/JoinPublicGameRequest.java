package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JoinPublicGameRequest{

  @JsonProperty
  private int numPlayers;
  
  @JsonProperty
  private String token;
  
  public int getNumPlayers(){
    return numPlayers;
  }

  public String getToken(){
    return token;
  }
  
}
