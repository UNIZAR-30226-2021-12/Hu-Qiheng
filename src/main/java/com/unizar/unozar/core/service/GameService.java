package com.unizar.unozar.core.service;

import com.unizar.unozar.core.controller.resources.CreateGameRequest;
import com.unizar.unozar.core.controller.resources.GameResponse;
import com.unizar.unozar.core.controller.resources.JoinGameRequest;
import com.unizar.unozar.core.controller.resources.PlayCardRequest;
import com.unizar.unozar.core.controller.resources.RoomResponse;
import com.unizar.unozar.core.controller.resources.TokenRequest;
import com.unizar.unozar.core.controller.resources.TokenResponse;

public interface GameService{
  
  public TokenResponse create(CreateGameRequest request);

  public RoomResponse readRoom(TokenRequest request);

  public GameResponse readGame(TokenRequest request);
  
  public TokenResponse join(JoinGameRequest request);

  public TokenResponse start(TokenRequest request);

  public TokenResponse playCard(PlayCardRequest request);

  public TokenResponse draw(TokenRequest request);
  
  public TokenResponse pause(TokenRequest request);
  
  public TokenResponse quit(TokenRequest request);

}
