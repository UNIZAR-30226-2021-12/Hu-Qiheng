package com.unizar.unozar.core.service;

import com.unizar.unozar.core.DTO.GameDTO;
import com.unizar.unozar.core.controller.resources.CreateGameRequest;
import com.unizar.unozar.core.controller.resources.JoinGameRequest;
import com.unizar.unozar.core.controller.resources.ReadGameRequest;

public interface GameService{
  
  public GameDTO createGame(CreateGameRequest request);

  public GameDTO readGame(String id, ReadGameRequest request);

  public String addPlayer();

  public GameDTO joinGame(JoinGameRequest request);

}
