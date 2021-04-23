package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeletePlayerRequest{

  @JsonProperty
  private String id;
  
  @JsonProperty
  private String token;
  
  public String getId(){
    return id;
  }
  
  public String getToken(){
    return token;
  }

}
