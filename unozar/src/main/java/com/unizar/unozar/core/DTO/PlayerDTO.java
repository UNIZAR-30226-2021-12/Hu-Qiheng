package com.unizar.unozar.core.DTO;

import com.unizar.unozar.core.entities.Player;

public class PlayerDTO {
  
  public String id;
  public String email;
  public String alias;
  public String token;
  public int private_wins;
  public int private_total;
  public int public_wins;
  public int public_total;

  public PlayerDTO(Player toTransfer) {
    id = toTransfer.getId();
    email = toTransfer.getEmail();
    alias = toTransfer.getAlias();
    token = null;
    private_wins = toTransfer.getPrivateWins();
    private_total = toTransfer.getPrivateTotal();
    public_wins = toTransfer.getPublicWins();
    public_total = toTransfer.getPublicTotal();
  }

}
