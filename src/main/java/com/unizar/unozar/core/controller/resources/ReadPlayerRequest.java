package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReadPlayerRequest{
  
  @JsonProperty
  public String id;
  
  public String getId(){
    return id;
  }

}
