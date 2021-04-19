package com.unizar.unozar.core.service;

import javax.servlet.http.HttpServletRequest;

import com.unizar.unozar.core.DTO.PlayerDTO;
import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.AuthenticationResponse;
import com.unizar.unozar.core.controller.resources.BasicPlayerRequest;
import com.unizar.unozar.core.controller.resources.CreatePlayerRequest;
import com.unizar.unozar.core.controller.resources.UpdatePlayerRequest;

public interface PlayerService{

  public PlayerDTO createPlayer(CreatePlayerRequest request);

  public PlayerDTO readPlayer(String id);
  
  public PlayerDTO updatePlayer(String id, UpdatePlayerRequest request);
  
  public Void deletePlayer(BasicPlayerRequest request);
  
  public AuthenticationResponse authentication(AuthenticationRequest request);
  
  public AuthenticationResponse refreshToken(HttpServletRequest request);

}
