package com.unizar.unozar.core.service;

import org.springframework.stereotype.Service;

import com.unizar.unozar.core.repository.GameRepository;

@Service
public class GameServiceImpl implements GameService{

  GameRepository gameRepository;
  
  public GameServiceImpl(GameRepository gameRepository){
    this.gameRepository = gameRepository;
  }
  
  @Override
  public String createGame() {
    
    return null;
  }

  public String addPlayer(){
    // TODO Auto-generated method stub
    return null;
  }

}
