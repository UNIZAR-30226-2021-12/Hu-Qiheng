package com.unizar.unozar.core.service;

import java.util.Optional;

import com.unizar.unozar.core.DTO.PlayerDTO;
import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.BasicPlayerRequest;
import com.unizar.unozar.core.controller.resources.CreatePlayerRequest;
import com.unizar.unozar.core.controller.resources.UpdatePlayerRequest;
import com.unizar.unozar.core.entities.Player;
import com.unizar.unozar.core.repository.PlayerRepository;

public class PlayerServiceImpl implements PlayerService{

  private final PlayerRepository playerRepository;
  
  public PlayerServiceImpl(PlayerRepository playerRepository){
    this.playerRepository = playerRepository;
  }
  
  @Override
  public PlayerDTO createPlayer(CreatePlayerRequest request){
    Player toCreate = playerRepository.save(new Player(request.getEmail(), 
        request.getAlias(), request.getPassword()));
    PlayerDTO player = new PlayerDTO(toCreate);
    return player;
  }

  public PlayerDTO updatePlayer(UpdatePlayerRequest request) {
    return null;
  }

  public Void deletePlayer(BasicPlayerRequest request) {
    return null;
  }

  public PlayerDTO authentication(AuthenticationRequest request) {
    return null;
  }

}
