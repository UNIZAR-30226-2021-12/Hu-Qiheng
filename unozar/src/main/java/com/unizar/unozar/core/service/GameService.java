package com.unizar.unozar.core.service;

import com.unizar.unozar.core.controller.resources.CreateGameRequest;

public interface GameService{
  
  public String createGame(CreateGameRequest request);
  
  public String addPlayer();
  
}
