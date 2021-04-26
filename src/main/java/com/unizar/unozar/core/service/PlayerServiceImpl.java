package com.unizar.unozar.core.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unizar.unozar.core.DTO.PlayerDTO;
import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.AuthenticationResponse;
import com.unizar.unozar.core.controller.resources.CreatePlayerRequest;
import com.unizar.unozar.core.controller.resources.DeletePlayerRequest;
import com.unizar.unozar.core.controller.resources.TokenRequest;
import com.unizar.unozar.core.controller.resources.UpdatePlayerRequest;
import com.unizar.unozar.core.entities.Player;
import com.unizar.unozar.core.exceptions.EmailInUse;
import com.unizar.unozar.core.exceptions.InvalidIdentity;
import com.unizar.unozar.core.exceptions.InvalidPassword;
import com.unizar.unozar.core.exceptions.InvalidToken;
import com.unizar.unozar.core.exceptions.PlayerNotFound;
import com.unizar.unozar.core.repository.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService{

  private final PlayerRepository playerRepository;
  
  public PlayerServiceImpl(PlayerRepository playerRepository){
    this.playerRepository = playerRepository;
  }
  
  @Override
  public PlayerDTO create(CreatePlayerRequest request){
    String emailNewUser = request.getEmail();
    Optional<Player> toFind = playerRepository.findByEmail(emailNewUser);
    if(toFind.isPresent()){
      throw new EmailInUse("The email is already in use");
    }
    Player created = playerRepository.save(new Player(request.getEmail(), 
        request.getAlias(), request.getPassword()));
    PlayerDTO player = new PlayerDTO(created);
    return player;
  }
  
  @Override
  public PlayerDTO read(String id){
    PlayerDTO player = new PlayerDTO(findPlayer(id));
    return player;
  }

  @Override
  public Void update(UpdatePlayerRequest request){
    String id = request.getToken().substring(0, 32);
    Player toUpdate = findPlayer(id);
    if(!toUpdate.checkSession(request.getToken().substring(32))){
      throw new InvalidToken("Invalid token");
    }
    if(request.getAlias() != null){
      toUpdate.setAlias(request.getAlias());
    }
    if(request.getEmail() != null){
      Optional<Player> otherPlayer = 
          playerRepository.findByEmail(request.getEmail());
      if(otherPlayer.isPresent()){
        throw new EmailInUse("The email is already in use");
      }
      toUpdate.setEmail(request.getEmail());
    }
    if(request.getPassword() != null){
      toUpdate.setPassword(request.getPassword());
    }
    playerRepository.save(toUpdate);
    return null;
  }

  @Override
  public Void delete(DeletePlayerRequest request){
    String id = request.getToken().substring(0, 32);
    Player toDelete = findPlayer(id);
    if(!toDelete.checkSession(request.getToken().substring(32))){
      throw new InvalidToken("Invalid token");
    }
    playerRepository.deleteById(id);
    return null;
  }

  @Override
  public AuthenticationResponse authentication(AuthenticationRequest request){
    Optional<Player> toFind = playerRepository.findByEmail(request.getEmail());
    if(!toFind.isPresent()){
      throw new PlayerNotFound("Email does not exist in the system");
    }
    Player toAuth = toFind.get();
    if(!toAuth.isValidPassword(request.getPassword())){
      throw new InvalidPassword("Invalid password");
    }
    String token = toAuth.getId() + toAuth.updateSession();
    playerRepository.save(toAuth);
    return new AuthenticationResponse(toAuth.getId(), token);
  }
  
  @Override
  public AuthenticationResponse refreshToken(TokenRequest request){
    String id = request.getToken().substring(0, 32);
    Player toRefresh = findPlayer(id);
    if(!toRefresh.checkSession(request.getToken().substring(32))){
      throw new InvalidIdentity("Invalid token");
    }
    String token = toRefresh.getId() + toRefresh.updateSession();
    playerRepository.save(toRefresh);
    return new AuthenticationResponse(toRefresh.getId(), token);
  }
  
  public Player findPlayer(String id){ 
    Optional<Player> toFind = playerRepository.findById(id);
    if (!toFind.isPresent()){
      throw new PlayerNotFound("Id does not exist in the system");
    }
    return toFind.get();
  }
  
}
