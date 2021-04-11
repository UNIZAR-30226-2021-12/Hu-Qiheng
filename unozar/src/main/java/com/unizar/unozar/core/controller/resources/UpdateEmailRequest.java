package com.unizar.unozar.core.controller.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateEmailRequest {

  @JsonProperty
  private String userId;
  
  @JsonProperty
  private int session;
  
}
