package com.unizar.unozar.core.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unizar.unozar.core.entities.Player;

public class PlayerDTO{
  
  @JsonProperty
  public String id;
  
  @JsonProperty
  public String email;
  
  @JsonProperty
  public String alias;
  
  @JsonProperty
  public int private_wins;
  
  @JsonProperty
  public int private_total;
  
  @JsonProperty
  public int public_wins;
  
  @JsonProperty
  public int public_total;

  public PlayerDTO(Player toTransfer){
    id = toTransfer.getId();
    email = toTransfer.getEmail();
    alias = toTransfer.getAlias();
    private_wins = toTransfer.getPrivateWins();
    private_total = toTransfer.getPrivateTotal();
    public_wins = toTransfer.getPublicWins();
    public_total = toTransfer.getPublicTotal();
  }

}
