package com.unizar.unozar.core.service;

import com.unizar.unozar.core.DTO.GameDTO;
import com.unizar.unozar.core.controller.resources.CreateGameRequest;
import com.unizar.unozar.core.controller.resources.JoinGameRequest;
import com.unizar.unozar.core.controller.resources.PlayCardRequest;
import com.unizar.unozar.core.controller.resources.ReadGameRequest;
import com.unizar.unozar.core.controller.resources.TokenRequest;

public interface GameService{
  
  public Void create(CreateGameRequest request);

  public GameDTO read(String id, ReadGameRequest request);

  public Void join(JoinGameRequest request);

  public Void start(TokenRequest request);

  public Void playCard(PlayCardRequest request);

  public Void quit(TokenRequest request);

}
