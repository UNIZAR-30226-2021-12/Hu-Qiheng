package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReadPlayerRequest{

  @JsonProperty
  private String playerId;
  
  public String getPlayerId(){
    return playerId;
  }
}
