package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DrawRequest{
  
  @JsonProperty
  private String token;

  @JsonProperty
  private boolean hasSaidUnozar;

  public String getToken(){
    return token;
  }

  public boolean getHasSaidUnozar(){
    return hasSaidUnozar;
  }

  
}
