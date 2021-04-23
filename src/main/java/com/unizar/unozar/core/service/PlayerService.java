package com.unizar.unozar.core.service;

import com.unizar.unozar.core.DTO.PlayerDTO;
import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.AuthenticationResponse;
import com.unizar.unozar.core.controller.resources.CreatePlayerRequest;
import com.unizar.unozar.core.controller.resources.DeletePlayerRequest;
import com.unizar.unozar.core.controller.resources.RefreshTokenRequest;
import com.unizar.unozar.core.controller.resources.UpdatePlayerRequest;

public interface PlayerService{

  public PlayerDTO createPlayer(CreatePlayerRequest request);

  public PlayerDTO readPlayer(String id);
  
  public Void updatePlayer(String id, UpdatePlayerRequest request);
  
  public Void deletePlayer(DeletePlayerRequest request);
  
  public AuthenticationResponse authentication(AuthenticationRequest request);
  
  public AuthenticationResponse refreshToken(RefreshTokenRequest request);

}
