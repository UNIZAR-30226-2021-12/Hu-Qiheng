package com.unizar.unozar.core.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unizar.unozar.core.DTO.PlayerDTO;
import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.AuthenticationResponse;
import com.unizar.unozar.core.controller.resources.CreatePlayerRequest;
import com.unizar.unozar.core.controller.resources.DeletePlayerRequest;
import com.unizar.unozar.core.controller.resources.RefreshTokenRequest;
import com.unizar.unozar.core.controller.resources.UpdatePlayerRequest;
import com.unizar.unozar.core.entities.Player;
import com.unizar.unozar.core.exceptions.Como;
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
  public PlayerDTO createPlayer(CreatePlayerRequest request){
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
  public PlayerDTO readPlayer(String id){
    Optional<Player> toFind = playerRepository.findById(id);
    if(!toFind.isPresent()){
      throw new PlayerNotFound("Id does not exist in the system");
    }
    Player toRead;
    try{
      toRead = toFind.get();
    }catch(Exception e){
      throw new Como("JAJAJAJAJA");
    }

    return new PlayerDTO(toRead);
  }

  @Override
  public Void updatePlayer(String id, UpdatePlayerRequest request){
    if(id != request.getToken().substring(0, 35)){
      throw new InvalidIdentity("The requester's id does not match with the " +
          "given id");      
    }    
    Optional<Player> toFind = playerRepository.findById(id);
    if (toFind.isPresent()){
      throw new PlayerNotFound("Id does not exist in the system");
    }
    Player toUpdate = toFind.get();
    if(!toUpdate.checkSession(request.getToken().substring(36))){
      throw new InvalidIdentity("Invalid token");
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
    return null;
  }

  @Override
  public Void deletePlayer(String id, DeletePlayerRequest request){
    if(id != request.getToken().substring(0, 35)){
      throw new InvalidIdentity("The requester's id does not match with the " +
          "given id");      
    }   
    Optional<Player> toFind = playerRepository.findById(id);
    if (toFind.isPresent()){
      throw new PlayerNotFound("Id does not exist in the system");
    }
    Player toDelete = toFind.get();
    if(!toDelete.checkSession(request.getToken().substring(36))){
      throw new InvalidToken("Invalid token");
    }
    playerRepository.deleteById(id);
    return null;
  }

  @Override
  public AuthenticationResponse 
      authentication(AuthenticationRequest request){
    Optional<Player> toFind = playerRepository.findByEmail(request.getEmail());
    if(toFind.isEmpty()){
      throw new PlayerNotFound("Email does not exist in the system");
    }
    Player toAuth = toFind.get();
    if(!toAuth.isValidPassword(request.getPassword())){
      throw new InvalidPassword("Invalid password");
    }
    String token = toAuth.getId() + toAuth.updateSession();
    return new AuthenticationResponse(token);
  }
  
  @Override
  public AuthenticationResponse 
      refreshToken(RefreshTokenRequest request){
    String id = request.getToken().substring(0, 35);
    Optional<Player> toFind = playerRepository.findById(id);
    if (toFind.isPresent()){
      throw new PlayerNotFound("Id does not exist in the system");
    }
    Player toRefresh = toFind.get();
    if(!toRefresh.checkSession(request.getToken().substring(36))){
      throw new InvalidIdentity("Invalid token");
    }
    String token = toRefresh.getId() + toRefresh.updateSession();
    return new AuthenticationResponse(token);
  }
  
}
