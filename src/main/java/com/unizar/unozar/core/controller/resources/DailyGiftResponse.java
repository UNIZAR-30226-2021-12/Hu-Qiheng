package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DailyGiftResponse{

  @JsonProperty
  private int gift;
  
  @JsonProperty
  private String token;
  
  public DailyGiftResponse(int gift, String token){
    this.gift = gift;
    this.token = token;
  }
}
