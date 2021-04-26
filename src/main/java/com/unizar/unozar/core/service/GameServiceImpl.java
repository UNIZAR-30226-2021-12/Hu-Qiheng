package com.unizar.unozar.core.service;

import org.springframework.stereotype.Service;

import com.unizar.unozar.core.DTO.GameDTO;
import com.unizar.unozar.core.controller.resources.CreateGameRequest;
import com.unizar.unozar.core.controller.resources.JoinGameRequest;
import com.unizar.unozar.core.controller.resources.ReadGameRequest;
import com.unizar.unozar.core.repository.GameRepository;

@Service
public class GameServiceImpl implements GameService{

  GameRepository gameRepository;
  
  public GameServiceImpl(GameRepository gameRepository){
    this.gameRepository = gameRepository;
  }
  
  @Override
  public GameDTO createGame(CreateGameRequest request){
    
    return null;
  }
  
  public GameDTO readGame(String id, ReadGameRequest request){
    return null;
  }

  public String addPlayer(){
    return null;
  }

  public GameDTO joinGame(JoinGameRequest request) {
    return null;
  }

}
