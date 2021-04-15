package com.unizar.unozar.core.service;

import com.unizar.unozar.core.DTO.PlayerDTO;
import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.BasicPlayerRequest;
import com.unizar.unozar.core.controller.resources.CreatePlayerRequest;
import com.unizar.unozar.core.controller.resources.UpdatePlayerRequest;

public interface PlayerService {

  public PlayerDTO createPlayer(CreatePlayerRequest request);
  

  public PlayerDTO readPlayer(String id);
  
  
  public PlayerDTO updatePlayer(String id, UpdatePlayerRequest request);

  
  public Void deletePlayer(BasicPlayerRequest request);

  
  public PlayerDTO authentication(AuthenticationRequest request);

}
