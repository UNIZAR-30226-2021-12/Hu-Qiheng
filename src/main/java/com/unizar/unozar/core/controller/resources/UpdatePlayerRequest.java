package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdatePlayerRequest{
  
  @JsonProperty
  private int avatar;  
  
  @JsonProperty
  private String email;
  
  @JsonProperty
  private String alias;
  
  @JsonProperty
  private String password;  
  
  @JsonProperty
  private String token;
  
  @JsonProperty
  private int board;  
  
  @JsonProperty
  private int cards;  

  public int getAvatar(){
    return avatar;
  }
  
  public String getEmail(){
    return email;
  }
  
  public String getAlias(){
    return alias;
  }
  
  public String getPassword(){
    return password;
  }
  
  public String getToken(){
    return token;
  }

  public int getBoard() {
    return board;
  }

  public int getCards() {
    return cards;
  }

}
