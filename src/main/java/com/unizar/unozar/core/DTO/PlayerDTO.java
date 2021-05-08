package com.unizar.unozar.core.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unizar.unozar.core.entities.Player;

public class PlayerDTO{
  
  @JsonProperty
  private String id;
  
  @JsonProperty
  private String email;
  
  @JsonProperty
  private int avatarId;
  
  @JsonProperty
  private String alias;
  
  @JsonProperty
  private int money;
  
  @JsonProperty
  private int[] unlockables;
  
  @JsonProperty
  private boolean giftClaimedToday;
  
  @JsonProperty
  private String gameId;
  
  @JsonProperty
  private int privateWins;
  
  @JsonProperty
  private int privateTotal;
  
  @JsonProperty
  private int publicWins;
  
  @JsonProperty
  private int publicTotal;
  
  public PlayerDTO(Player toTransfer){
    id = toTransfer.getId();
    email = toTransfer.getEmail();
    avatarId = toTransfer.getAvatarId();
    alias = toTransfer.getAlias();
    money = toTransfer.getMoney();
    unlockables = toTransfer.getUnlockables().stream().mapToInt(i->i).toArray();
    giftClaimedToday = toTransfer.isGiftClaimedToday();
    gameId = toTransfer.getGameId();
    privateWins = toTransfer.getPrivateWins();
    privateTotal = toTransfer.getPrivateTotal();
    publicWins = toTransfer.getPublicWins();
    publicTotal = toTransfer.getPublicTotal();
  }

}
