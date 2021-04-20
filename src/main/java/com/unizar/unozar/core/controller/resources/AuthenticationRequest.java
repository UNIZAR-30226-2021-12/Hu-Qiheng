package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationRequest{
  
  @JsonProperty
  private String email;
  
  @JsonProperty
  private String password;

  public String getEmail(){
    return email;
  }

  public String getPassword(){
    return password;
  }
  
}
