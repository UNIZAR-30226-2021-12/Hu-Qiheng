package com.unizar.unozar.core.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unizar.unozar.core.DTO.GameDTO;
import com.unizar.unozar.core.controller.resources.CreateGameRequest;
import com.unizar.unozar.core.controller.resources.JoinGameRequest;
import com.unizar.unozar.core.controller.resources.PlayCardRequest;
import com.unizar.unozar.core.controller.resources.ReadGameRequest;
import com.unizar.unozar.core.controller.resources.TokenRequest;
import com.unizar.unozar.core.entities.Game;
import com.unizar.unozar.core.entities.Player;
import com.unizar.unozar.core.exceptions.PlayerInGame;
import com.unizar.unozar.core.exceptions.PlayerNotFound;
import com.unizar.unozar.core.repository.GameRepository;
import com.unizar.unozar.core.repository.PlayerRepository;

@Service
public class GameServiceImpl implements GameService{

  private final GameRepository gameRepository;
  
  private final PlayerRepository playerRepository;
  
  public GameServiceImpl(GameRepository gameRepository, 
      PlayerRepository playerRepository){
    this.gameRepository = gameRepository;
    this.playerRepository = playerRepository;
  }
  
  @Override
  public Void create(CreateGameRequest request){
    String id = request.getToken().substring(0, 32);
    Player owner = findPlayer(id);
    checkPlayerNotInGame(owner);
    Game toCreate = new Game(request.getIsPrivate(), request.getMaxPlayers(),
        request.getNumBots(), id);
    gameRepository.save(toCreate);
    owner.setGameId(toCreate.getId());
    playerRepository.save(owner);
    return null;
  }
  
  @Override
  public GameDTO read(String id, ReadGameRequest request){
    
    return null;
  }

  @Override
  public Void join(JoinGameRequest request){
    
    return null;
  }

  @Override
  public Void start(TokenRequest request){
    
    return null;
  }
  
  @Override
  public Void playCard(PlayCardRequest request){
    
    return null;
  }
  
  @Override
  public Void quit(TokenRequest request){
    
    return null;
  }
  
  public Player findPlayer(String id){ 
    Optional<Player> toFind = playerRepository.findById(id);
    if (!toFind.isPresent()){
      throw new PlayerNotFound("Id does not exist in the system");
    }
    return toFind.get();
  }
  
  public Void checkPlayerNotInGame(Player toCheck){
    if(toCheck.isInAGame()){
      throw new PlayerInGame("The player is currently on a game");
    }
    return null;
  }
  
}
