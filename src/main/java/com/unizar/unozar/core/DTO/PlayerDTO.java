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
  public String gameId;
  
  @JsonProperty
  public int privateWins;
  
  @JsonProperty
  public int privateTotal;
  
  @JsonProperty
  public int publicWins;
  
  @JsonProperty
  public int publicTotal;
  
  public PlayerDTO(Player toTransfer){
    id = toTransfer.getId();
    email = toTransfer.getEmail();
    alias = toTransfer.getAlias();
    gameId = toTransfer.getGameId();
    privateWins = toTransfer.getPrivateWins();
    privateTotal = toTransfer.getPrivateTotal();
    publicWins = toTransfer.getPublicWins();
    publicTotal = toTransfer.getPublicTotal();
  }

}
