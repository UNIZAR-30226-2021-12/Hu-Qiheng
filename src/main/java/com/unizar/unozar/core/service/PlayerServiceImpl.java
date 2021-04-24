package com.unizar.unozar.core.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unizar.unozar.core.DTO.PlayerDTO;
import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.AuthenticationResponse;
import com.unizar.unozar.core.controller.resources.CreatePlayerRequest;
import com.unizar.unozar.core.controller.resources.DeletePlayerRequest;
import com.unizar.unozar.core.controller.resources.RefreshTokenRequest;
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
    PlayerDTO player = new PlayerDTO(toFind.get());
    return player;
  }

  @Override
  public Void updatePlayer(String id, UpdatePlayerRequest request){
    if(!id.equals(request.getToken().substring(0, 32))){
      throw new InvalidIdentity("The requester's id does not match with the " +
          "given id");      
    }    
    Optional<Player> toFind = playerRepository.findById(id);
    if (!toFind.isPresent()){
      throw new PlayerNotFound("Id does not exist in the system");
    }
    Player toUpdate = toFind.get();
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
  public Void deletePlayer(String id, DeletePlayerRequest request){
    if(!id.equals(request.getToken().substring(0, 32))){
      throw new InvalidIdentity("The requester's id does not match with the " +
          "given id"); 
    }
    Optional<Player> toFind = playerRepository.findById(id);
    if(!toFind.isPresent()){
      throw new PlayerNotFound("Id does not exist in the system");
    }
    Player toDelete = toFind.get();
    if(!toDelete.checkSession(request.getToken().substring(32))){
      throw new InvalidToken("Invalid token");
    }
    playerRepository.deleteById(id);
    return null;
  }

  @Override
  public AuthenticationResponse 
      authentication(AuthenticationRequest request){
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
    return new AuthenticationResponse(token);
  }
  
  @Override
  public AuthenticationResponse 
      refreshToken(RefreshTokenRequest request){
    System.out.println("Aqui");
    String id = request.getToken().substring(0, 32);
    System.out.println("Aqui2");
    Optional<Player> toFind = playerRepository.findById(id);
    System.out.println("Aqui3");
    if (!toFind.isPresent()){
      throw new PlayerNotFound("Id does not exist in the system");
    }
    System.out.println("Aqui4");
    Player toRefresh = toFind.get();
    System.out.println("Aqui5");
    if(!toRefresh.checkSession(request.getToken().substring(32))){
      throw new InvalidIdentity("Invalid token");
    }
    System.out.println("Aqui6");
    String token = toRefresh.getId() + toRefresh.updateSession();
    System.out.println("Aqui7");
    playerRepository.save(toRefresh);
    System.out.println("Aqui8");
    return new AuthenticationResponse(token);
  }
  
}
