package com.unizar.unozar.core.service;

import com.unizar.unozar.core.controller.resources.AddPlayerRequest;
import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.CreateGameRequest;
import com.unizar.unozar.core.controller.resources.DeletePlayerRequest;
import com.unizar.unozar.core.controller.resources.GetAliasRequest;
import com.unizar.unozar.core.controller.resources.RegisterRequest;
import com.unizar.unozar.core.controller.resources.StatisticsRequest;
import com.unizar.unozar.core.controller.resources.UpdateEmailRequest;
import com.unizar.unozar.core.controller.resources.UpdatePasswordRequest;
import com.unizar.unozar.core.entities.Player;
import com.unizar.unozar.core.repository.PlayerRepository;

public class PlayerServiceImpl implements PlayerService{

  private final PlayerRepository playerRepository;
  
  public PlayerServiceImpl(PlayerRepository playerRepository){
    this.playerRepository = playerRepository;
  }
  
  @Override
  public String createPlayer(RegisterRequest request){
    if(playerRepository.save(new Player(request.getEmail(), request.getAlias(),
        request.getPassword())) != null){
      return "PLAYER CREATED";
    }
    return null;
  }

  public String updatePlayerEmail(UpdateEmailRequest request){
    // TODO Auto-generated method stub
    return null;
  }
  
  public String updatePlayerPassword(UpdatePasswordRequest request){
    // TODO Auto-generated method stub
    return null;
  }
  
  public String deletePlayer(DeletePlayerRequest request){
    // TODO Auto-generated method stub
    return null;
  }

  public String findByEmail(String email){
    // TODO Auto-generated method stub
    return null;
  }

  public String authentication(AuthenticationRequest request){
    // TODO Auto-generated method stub
    return null;
  }

  public String getStatistics(StatisticsRequest request){
    // TODO Auto-generated method stub
    return null;
  }

  public String getAlias(GetAliasRequest request){
    // TODO Auto-generated method stub
    return null;
  }

  public String createGame(CreateGameRequest request){
    // TODO Auto-generated method stub
    return null;
  }

  public String addPlayer(AddPlayerRequest request){
    // TODO Auto-generated method stub
    return null;
  }

}
