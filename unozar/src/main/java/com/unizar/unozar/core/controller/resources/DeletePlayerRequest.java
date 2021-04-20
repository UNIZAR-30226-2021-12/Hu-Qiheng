package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeletePlayerRequest{

  @JsonProperty
  private String password;
  
  public String getPassword(){
    return password;
  }

}
