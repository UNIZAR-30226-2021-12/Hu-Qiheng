package com.unizar.unozar.core.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unizar.unozar.core.DTO.GameDTO;
import com.unizar.unozar.core.controller.resources.CreateGameRequest;
import com.unizar.unozar.core.controller.resources.JoinGameRequest;
import com.unizar.unozar.core.controller.resources.PlayCardRequest;
import com.unizar.unozar.core.controller.resources.TokenRequest;
import com.unizar.unozar.core.entities.Game;
import com.unizar.unozar.core.entities.Player;
import com.unizar.unozar.core.exceptions.GameNotFound;
import com.unizar.unozar.core.exceptions.InvalidIdentity;
import com.unizar.unozar.core.exceptions.PlayerIsNotPlaying;
import com.unizar.unozar.core.exceptions.PlayerIsPlaying;
import com.unizar.unozar.core.exceptions.PlayerNotFound;
import com.unizar.unozar.core.exceptions.PlayerNotInGame;
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
  public GameDTO read(TokenRequest request){
    Player inGame = findPlayer(request.getToken().substring(0, 32));
    checkToken(inGame, request.getToken().substring(32));
    checkPlayerInGame(inGame);
    Optional<Game> toFind = gameRepository.findById(inGame.getGameId());
    if(!toFind.isPresent()){
      throw new GameNotFound("The game does not exist");
    }
    Game toRead = toFind.get();
    int playerNum = toRead.getPlayerNum(inGame.getId());
    if(playerNum == -1){
      throw new PlayerNotInGame("The player is not in the game");
    }
    GameDTO read = new GameDTO(toRead, playerNum);
    return read;
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
  
  public Void checkToken(Player toCheck, String token){
    if(!toCheck.checkSession(token)){
      throw new InvalidIdentity("Invalid token");
    }
    return null;
  }
  
  public Void checkPlayerNotInGame(Player toCheck){
    if(toCheck.isInAGame()){
      throw new PlayerIsPlaying("The player is currently on a game");
    }
    return null;
  }
  
  public Void checkPlayerInGame(Player toCheck){
    if(!toCheck.isInAGame()){
      throw new PlayerIsNotPlaying("The player is not on a game");
    }
    return null;
  }
  
}
