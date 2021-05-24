package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayCardRequest{
  
  @JsonProperty
  private String token;
  
  @JsonProperty
  private int cardToPlay;
  
  @JsonProperty
  private boolean hasSaidUnozar;
  
  @JsonProperty
  private String colorSelected;

  public String getToken(){
    return token;
  }

  public int getCardToPlay(){
    return cardToPlay;
  }

  public boolean getHasSaidUnozar(){
    return hasSaidUnozar;
  }

  public String getColorSelected(){
    return colorSelected;
  }
  
}
