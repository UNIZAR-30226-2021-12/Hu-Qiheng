package com.unizar.unozar.core.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unizar.unozar.core.entities.Player;

public class PlayerDTO{
  
  @JsonProperty
  public String id;
  
  @JsonProperty
  public String email;
  
  @JsonProperty
  public int avatarId;
  
  @JsonProperty
  public String alias;
  
  @JsonProperty
  public int money;
  
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
    System.out.println(id);
    email = toTransfer.getEmail();
    System.out.println(email);    
    avatarId = toTransfer.getAvatarId();
    System.out.println(avatarId);
    alias = toTransfer.getAlias();
    System.out.println(alias);
    money = toTransfer.getMoney();
    System.out.println(money);
    gameId = toTransfer.getGameId();
    System.out.println(gameId);
    privateWins = toTransfer.getPrivateWins();
    System.out.println(privateWins);
    privateTotal = toTransfer.getPrivateTotal();
    System.out.println(privateTotal);
    publicWins = toTransfer.getPublicWins();
    System.out.println(publicWins);
    publicTotal = toTransfer.getPublicTotal();
    System.out.println(publicTotal);
  }

}
