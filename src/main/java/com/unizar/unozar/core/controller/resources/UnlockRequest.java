package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UnlockRequest{

  @JsonProperty
  private int unlockableId;
  
  @JsonProperty
  private String token;
  
  public int getUnlockableId(){
    return unlockableId;
  }
  
  public String getToken(){
    return token;
  }
  
}
