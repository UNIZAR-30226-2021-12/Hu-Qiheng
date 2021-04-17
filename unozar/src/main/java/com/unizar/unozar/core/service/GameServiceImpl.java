package com.unizar.unozar.core.service;

import com.unizar.unozar.core.controller.resources.CreateGameRequest;
import com.unizar.unozar.core.repository.GameRepository;

public class GameServiceImpl implements GameService{

  GameRepository gameRepository;
  
  public GameServiceImpl(GameRepository gameRepository){
    this.gameRepository = gameRepository;
  }
  
  @Override
  public String createGame(CreateGameRequest request) {
    
    return null;
  }

  public String addPlayer(){
    // TODO Auto-generated method stub
    return null;
  }

}
