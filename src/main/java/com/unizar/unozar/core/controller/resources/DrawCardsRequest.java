package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DrawCardsRequest{
  
  @JsonProperty
  private String token;
  
  @JsonProperty
  private int cardsToDraw;
  
  @JsonProperty
  private boolean hasSaidUnozar;

  public String getToken(){
    return token;
  }

  public int getCardsToDraw(){
    return cardsToDraw;
  }

  public boolean getHasSaidUnozar(){
    return hasSaidUnozar;
  }

  
}
