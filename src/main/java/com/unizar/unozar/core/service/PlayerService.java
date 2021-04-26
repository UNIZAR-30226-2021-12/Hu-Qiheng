package com.unizar.unozar.core.service;

import com.unizar.unozar.core.DTO.PlayerDTO;
import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.AuthenticationResponse;
import com.unizar.unozar.core.controller.resources.CreatePlayerRequest;
import com.unizar.unozar.core.controller.resources.DeletePlayerRequest;
import com.unizar.unozar.core.controller.resources.TokenRequest;
import com.unizar.unozar.core.controller.resources.UpdatePlayerRequest;

public interface PlayerService{

  public PlayerDTO create(CreatePlayerRequest request);

  public PlayerDTO read(String id);
  
  public Void update(UpdatePlayerRequest request);
  
  public Void delete(DeletePlayerRequest request);
  
  public AuthenticationResponse authentication(AuthenticationRequest request);
  
  public AuthenticationResponse refreshToken(TokenRequest request);

}
