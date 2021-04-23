package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePlayerRequest{

  @JsonProperty
  private String email;
  
  @JsonProperty
  private String alias;
  
  @JsonProperty
  private String password;

  public String getEmail(){
    return email;
  }
  
  public String getAlias(){
    return alias;
  }
  
  public String getPassword(){
    return password;
  }
  
}
