package com.unizar.unozar.core.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "GAME")
public class Game {
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;
  
  @Column(name = "MAX_PLAYERS")
  private int maxPlayers;
  
  @Column(name = "NUM_BOTS")
  private int numBots;
  
  @Column(name = "PLAYERS")
  private UUID players[];
  
  public Game(int maxPlayers, int numBots, UUID player){
    this.maxPlayers = maxPlayers;
    this.numBots = numBots;
    players = new UUID[maxPlayers];
    players[0] = player;
    for (int i = 1 + numBots; i < maxPlayers; i++){
      players[i] = null;
    }
  }
  
  public boolean addPlayer(UUID player){
    for(int i = 1 + numBots; i < maxPlayers; i++){
      if(players[i] == null){
        players[i] = player;
        return true;
      }
    }
    return false;
  }
}
