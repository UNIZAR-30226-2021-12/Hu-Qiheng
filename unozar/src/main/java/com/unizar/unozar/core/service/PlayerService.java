package com.unizar.unozar.core.service;

import javax.servlet.http.HttpServletRequest;

import com.unizar.unozar.core.DTO.PlayerDTO;
import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.AuthenticationResponse;
import com.unizar.unozar.core.controller.resources.BasicPlayerRequest;
import com.unizar.unozar.core.controller.resources.DeletePlayerRequest;

public interface PlayerService{

  public PlayerDTO createPlayer(BasicPlayerRequest request);

  public PlayerDTO readPlayer(String id);
  
  public Void updatePlayer(String id, BasicPlayerRequest request);
  
  public Void deletePlayer(String id, DeletePlayerRequest request);
  
  public AuthenticationResponse authentication(AuthenticationRequest request);
  
  public AuthenticationResponse refreshToken(HttpServletRequest request);

}
