package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse{

  @JsonProperty
  private String id;
  
  @JsonProperty
  private String token;
    
  public AuthenticationResponse(String id, String token){
    this.id = id;
    this.token = token;
  }

}
