package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FriendRequest {
  
  @JsonProperty
  private String token;
  
  @JsonProperty
  private String friendId;  
  
  public String getToken(){
    return token;
  }
  
  public String getFriendId() {
    return friendId;
  }

}
